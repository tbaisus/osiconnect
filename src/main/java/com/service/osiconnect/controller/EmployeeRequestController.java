package com.service.osiconnect.controller;

import com.service.osiconnect.dto.EmployeeDTO;
import com.service.osiconnect.dto.EmployeeRequestDTO;
import com.service.osiconnect.dto.OsiConnectDTO;
import com.service.osiconnect.exception.ApplicationException;
import com.service.osiconnect.model.EmployeeModel;
import com.service.osiconnect.model.EmployeeRequestModel;
import com.service.osiconnect.serviceimpl.EmployeeRequestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin(origins = "http://localhost:4200") // Allow requests from this origin
public class EmployeeRequestController {

    @Autowired
    private EmployeeRequestServiceImpl employeeRequestService;


    @GetMapping(value = "/allemployeesrequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getAllEmployeesRequest() {
        List<EmployeeRequestModel> employeeRequestModels = employeeRequestService.getAllEmployeeRequests();
        return new ResponseEntity<>(employeeRequestModels, HttpStatus.OK);
    }

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getEmployeeRequests(@RequestParam(value = "employeeId") Long employeeId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "department") String department) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        List<EmployeeRequestModel> employeeRequests;
        if (StringUtils.isBlank(department)) {
            employeeRequests = employeeRequestService.getRequestsOfEmpByStatus(employee, status);
            return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
        } else if (StringUtils.isBlank(status)) {
            employeeRequests = employeeRequestService.getRequestsOfEmpByDepartment(employee, department);
            return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
        }
        employeeRequests = employeeRequestService.getRequestsOfEmp(employee);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }


    @GetMapping(value = "/requestsbydept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getRequestsOfEmployeeByDepartment(@RequestParam(value = "employeeId") Long employeeId, @RequestParam(value = "department") String department) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        log.info("printing");
        List<EmployeeRequestModel> employeeRequests = employeeRequestService.getRequestsOfEmpByDepartment(employee, department);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }


    @PostMapping(value = "/createrequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> saveEmployeeRequest( @RequestParam Long employeeId,  @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
      log.info("empId {}",employeeId);
        if (employeeId == null) {
            throw new ApplicationException("employee id missing", "ERR_MISSING", HttpStatus.BAD_REQUEST);
        }
        log.info("saving request");
        EmployeeRequestModel employeeRequestModel = employeeRequestService.saveEmployeeRequest(employeeId, employeeRequestDTO);
        Map<String,Object> objectMap =new HashMap<>();
        objectMap.put("ticket_Id",employeeRequestModel.getRequestId());
        objectMap.put("ticket_summary",employeeRequestModel.getSummary());
        objectMap.put("ticket_description",employeeRequestModel.getDescription());
        objectMap.put("practice",employeeRequestModel.getDepartment());

        return new ResponseEntity<>(objectMap, HttpStatus.CREATED);
    }

    @PostMapping(value = "/saveemployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeModel> saveEmployeeRequest(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeModel saveEmployee = employeeRequestService.saveEmployee(employeeDTO);
        return new ResponseEntity<>(saveEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteemployeerequestbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteEmployeeRequest(@Valid @RequestParam(value = "requestId") Long requestId) {
        return new ResponseEntity<>(employeeRequestService.deleteEmployeeRequest(requestId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateemployeerequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeRequestModel> updateEmployeeRequest(@RequestParam(value = "requestId") Long requestId, @Valid @RequestBody OsiConnectDTO osiConnectDTO) {
        EmployeeRequestModel employeeRequestModel = employeeRequestService.updateEmployeeRequest(requestId, osiConnectDTO);
        return new ResponseEntity<>(employeeRequestModel, HttpStatus.OK);
    }

}



