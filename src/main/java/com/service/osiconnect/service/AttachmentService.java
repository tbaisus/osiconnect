package com.service.osiconnect.service;

import com.service.osiconnect.model.AttachmentModel;
import com.service.osiconnect.model.EmployeeRequestModel;

import java.util.List;
import java.util.Optional;

public interface AttachmentService {

    public List<AttachmentModel> getAttachments(Optional<EmployeeRequestModel> employeeRequestModel);
}
