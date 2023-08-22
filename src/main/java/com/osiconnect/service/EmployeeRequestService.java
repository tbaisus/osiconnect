package com.osiconnect.service;


import com.osiconnect.dto.EmployeeRequestDTO;
import com.osiconnect.model.EmployeeRequestModel;
import com.osiconnect.dto.OsiConnectDTO;

import java.util.List;
import java.util.Map;


public interface EmployeeRequestService {
    public List<EmployeeRequestModel> getAllEmployeeRequests();

    public EmployeeRequestModel saveEmployeeRequest(Long employeeId,EmployeeRequestDTO employeeRequestDTO);

    public Map<String, Object> deleteEmployeeRequest(Long requestid);

    public EmployeeRequestModel updateEmployeeRequest(Long requestId, OsiConnectDTO osiConnectDTO);


}
