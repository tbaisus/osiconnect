package com.service.osiconnect.service;

import com.service.osiconnect.dto.EmployeeRequestDTO;
import com.service.osiconnect.dto.OsiConnectDTO;
import com.service.osiconnect.model.EmployeeRequestModel;

import java.util.List;
import java.util.Map;


public interface EmployeeRequestService {
    public List<EmployeeRequestModel> getAllEmployeeRequests();

    public EmployeeRequestModel saveEmployeeRequest(Long employeeId, EmployeeRequestDTO employeeRequestDTO);

    public Map<String, Object> deleteEmployeeRequest(Long requestid);

    public EmployeeRequestModel updateEmployeeRequest(Long requestId, OsiConnectDTO osiConnectDTO);


}
