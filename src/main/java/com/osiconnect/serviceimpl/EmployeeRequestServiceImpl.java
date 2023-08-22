package com.osiconnect.serviceimpl;

import com.osiconnect.dto.EmployeeDTO;
import com.osiconnect.dto.EmployeeRequestDTO;
import com.osiconnect.dto.OsiConnectDTO;
import com.osiconnect.exception.ApplicationException;
import com.osiconnect.model.*;
import com.osiconnect.repository.EmployeeRepository;
import com.osiconnect.repository.EmployeeRequestRepository;
import com.osiconnect.service.EmployeeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.osiconnect.constants.ApplicationConstants.*;
import static com.osiconnect.standards.ApplicationUtil.getFormattedDateTime;

@Service
public class EmployeeRequestServiceImpl implements EmployeeRequestService {


    @Autowired
    private EmployeeRequestRepository employeeRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * To get all employee requests
     *
     * @param
     * @return all the employee requests associated with the employees
     * @see ApplicationException
     */
    public List<EmployeeRequestModel> getAllEmployeeRequests() {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findAll();
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }


    /**
     * To get the employee request detail
     *
     * @param requestId is the parameter passed to get the employee request detail
     * @return the employee request detail
     * @see ApplicationException
     */
    public Optional<EmployeeRequestModel> findEmployeeRequestById(Long requestId) {
        Optional<EmployeeRequestModel> employeeRequestModel = employeeRequestRepository.findById(requestId);
        if (employeeRequestModel.isPresent()) {
            return employeeRequestModel;
        } else {
            throw new ApplicationException(ID_NOT_EXIST, ERR_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * To get all employee requests of a particular employee
     *
     * @param employee object passed to find by employee
     * @return requests of specific employee
     * @see ApplicationException
     */
    public List<EmployeeRequestModel> getAllRequestsOfEmp(EmployeeModel employee) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployee(employee);
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }

    public List<EmployeeRequestModel> getAllClosedRequestsOfEmp(EmployeeModel employee) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployeeAndStatus(employee, CLOSED);
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }


    public List<EmployeeRequestModel> getAllPendingRequestsOfEmp(EmployeeModel employee) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployeeAndStatus(employee, PENDING);
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }


    public List<EmployeeRequestModel> getRequestsOfEmpByDepartment(EmployeeModel employee, String department) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployeeAndDepartment(employee, department);
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }


    public EmployeeModel getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ApplicationException(EMP_NOT_FOUND, ERR_EMP_NOT_FOUND, HttpStatus.BAD_REQUEST));

    }


    @Transactional
    public EmployeeRequestModel saveEmployeeRequest(Long employeeId, EmployeeRequestDTO employeeRequestDTO) {
        EmployeeModel employee = getEmployeeById(employeeId);
        if (employee == null) {
            throw new ApplicationException(EMP_NOT_FOUND, ERR_EMP_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        LocalDate expectedResolutionDate = LocalDate.parse(employeeRequestDTO.getExpectedResolutionDate());
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel();
        employeeRequestModel.setDescription(employeeRequestDTO.getDescription());
        employeeRequestModel.setPriority(employeeRequestDTO.getPriority());
        employeeRequestModel.setDepartment(employeeRequestDTO.getDepartment());
        employeeRequestModel.setRequestedDate(getFormattedDateTime());
        employeeRequestModel.setExpectedResolutionDate(expectedResolutionDate);
        employeeRequestModel.setPriority(employeeRequestDTO.getPriority());
        employeeRequestModel.setSeverity(employeeRequestDTO.getSeverity());
        employeeRequestModel.setSummary(employeeRequestDTO.getSummary());
        employeeRequestModel.setCategory(employeeRequestDTO.getCategory());
        employeeRequestModel.setSubCategory(employeeRequestDTO.getSubCategory());
        employeeRequestModel.setEmployee(employee);
        return employeeRequestRepository.save(employeeRequestModel);

    }

    @Transactional
    public EmployeeModel saveEmployee(EmployeeDTO employeeDTO) {
        EmployeeModel employee = new EmployeeModel();
        employee.setEmployeeName(employeeDTO.getName());
        return employeeRepository.save(employee);

    }

    public Map<String, Object> deleteEmployeeRequest(Long requestId) {
        Optional<EmployeeRequestModel> employeeRequest = findEmployeeRequestById(requestId);
        if (employeeRequest.isPresent()) {
            employeeRequestRepository.deleteById(requestId);
            Map<String, Object> response = new HashMap<>();
            response.put(MSG, EMP_REQUEST_DELETE);
            response.put(SUCCESS, true);
            return response;
        } else {
            throw new ApplicationException(ID_NOT_EXIST, ERR_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
    }


    @Transactional
    public EmployeeRequestModel updateEmployeeRequest(Long requestId, OsiConnectDTO osiConnectDTO) {

        Optional<EmployeeRequestModel> existingRequestOptional = findEmployeeRequestById(requestId);

        if (existingRequestOptional.isPresent()) {
            EmployeeRequestModel existingRequest = existingRequestOptional.get();
            if (osiConnectDTO.getSla() != null) {
                existingRequest.setSla(osiConnectDTO.getSla());
            }
            if (osiConnectDTO.getStatus() != null) {
                existingRequest.setStatus(osiConnectDTO.getStatus());
            }
            return employeeRequestRepository.save(existingRequest);
        } else {
            throw new ApplicationException(ID_NOT_EXIST, ERR_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
    }


}



