package com.service.osiconnect.serviceimpl;

import com.service.osiconnect.dto.EmployeeDTO;
import com.service.osiconnect.dto.EmployeeRequestDTO;
import com.service.osiconnect.dto.OsiConnectDTO;
import com.service.osiconnect.exception.ApplicationException;
import com.service.osiconnect.model.EmployeeModel;
import com.service.osiconnect.model.EmployeeRequestModel;
import com.service.osiconnect.repository.EmployeeRepository;
import com.service.osiconnect.repository.EmployeeRequestRepository;
import com.service.osiconnect.service.EmployeeRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.service.osiconnect.constants.ApplicationConstants.*;
import static com.service.osiconnect.standards.ApplicationUtil.getFormattedDateTime;

@Service
@Slf4j
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
    public List<EmployeeRequestModel> getRequestsOfEmp(EmployeeModel employee) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployee(employee);
        if (employeeRequests == null || employeeRequests.isEmpty()) {
            throw new ApplicationException(NO_EMPLOYEE_REQUESTS, ERR_NO_EMPLOYEE_REQUESTS, HttpStatus.BAD_REQUEST);
        }
        return employeeRequests;
    }


    public List<EmployeeRequestModel> getRequestsOfEmpByStatus(EmployeeModel employee,String status) {
        List<EmployeeRequestModel> employeeRequests = employeeRequestRepository.findByEmployeeAndStatus(employee, status);
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
      log.info("getting details of employee");
        return employeeRepository.findById(id).orElseThrow(() -> new ApplicationException(EMP_NOT_FOUND, ERR_EMP_NOT_FOUND, HttpStatus.BAD_REQUEST));

    }


    @Transactional
    public EmployeeRequestModel saveEmployeeRequest(Long employeeId, EmployeeRequestDTO employeeRequestDTO) {
        EmployeeModel employee = getEmployeeById(employeeId);
        log.info("info {}",employee);
        if (employee == null) {
            throw new ApplicationException(EMP_NOT_FOUND, ERR_EMP_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
       // LocalDate expectedResolutionDate = LocalDate.parse(employeeRequestDTO.getExpectedResolutionDate());
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel();
        employeeRequestModel.setDescription(employeeRequestDTO.getDescription());
        employeeRequestModel.setPriority(employeeRequestDTO.getPriority());
        if(StringUtils.isBlank(employeeRequestDTO.getDepartment())){
          employeeRequestModel.setDepartment("OSI Connect");
        }else {
          employeeRequestModel.setDepartment(employeeRequestDTO.getDepartment());
        }
        employeeRequestModel.setRequestedDate(getFormattedDateTime());
      //  employeeRequestModel.setExpectedResolutionDate(expectedResolutionDate);
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



