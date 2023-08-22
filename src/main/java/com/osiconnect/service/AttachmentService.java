package com.osiconnect.service;

import com.osiconnect.model.AttachmentModel;
import com.osiconnect.model.EmployeeRequestModel;

import java.util.List;
import java.util.Optional;

public interface AttachmentService {

    public List<AttachmentModel> getAttachments(Optional<EmployeeRequestModel> employeeRequestModel);
}
