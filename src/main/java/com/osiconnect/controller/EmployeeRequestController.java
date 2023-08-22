package com.osiconnect.controller;

import com.osiconnect.dto.EmployeeDTO;
import com.osiconnect.dto.EmployeeRequestDTO;
import com.osiconnect.dto.OsiConnectDTO;
import com.osiconnect.exception.ApplicationException;
import com.osiconnect.model.*;
import com.osiconnect.serviceimpl.EmployeeRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRequestController {

    @Autowired
    private EmployeeRequestServiceImpl employeeRequestService;


    @GetMapping(value = "/allemployeesrequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getAllEmployeesRequest() {
        List<EmployeeRequestModel> employeeRequestModels = employeeRequestService.getAllEmployeeRequests();
        return new ResponseEntity<>(employeeRequestModels, HttpStatus.OK);
    }

    @GetMapping(value = "/allrequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getEmployeeRequests(@RequestParam(value = "employeeId") Long employeeId) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        List<EmployeeRequestModel> employeeRequests = employeeRequestService.getAllRequestsOfEmp(employee);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/closedrequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getClosedRequestsOfEmployee(@RequestParam(value = "employeeId") Long employeeId) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        List<EmployeeRequestModel> employeeRequests = employeeRequestService.getAllClosedRequestsOfEmp(employee);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/pendingrequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getPendingRequestsOfEmployee(@RequestParam(value = "employeeId") Long employeeId) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        List<EmployeeRequestModel> employeeRequests = employeeRequestService.getAllPendingRequestsOfEmp(employee);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }


    @GetMapping(value = "/requestsbydept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequestModel>> getRequestsOfEmployeeByDepartment(@RequestParam(value = "employeeId") Long employeeId,@RequestParam(value = "department") String department) {
        EmployeeModel employee = employeeRequestService.getEmployeeById(employeeId);
        List<EmployeeRequestModel> employeeRequests = employeeRequestService.getRequestsOfEmpByDepartment(employee,department);
        return new ResponseEntity<>(employeeRequests, HttpStatus.OK);
    }




    @PostMapping(value = "/createrequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeRequestModel> saveEmployeeRequest(@Valid @RequestParam Long employeeId, @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        if(employeeId==null){
            throw new ApplicationException("employee id missing","ERR_MISSING",HttpStatus.BAD_REQUEST);
        }
        EmployeeRequestModel employeeRequestModel = employeeRequestService.saveEmployeeRequest(employeeId, employeeRequestDTO);
        return new ResponseEntity<>(employeeRequestModel, HttpStatus.CREATED);
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



