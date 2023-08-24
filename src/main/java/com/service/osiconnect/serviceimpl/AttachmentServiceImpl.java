package com.service.osiconnect.serviceimpl;

import com.service.osiconnect.exception.ApplicationException;
import com.service.osiconnect.model.AttachmentModel;
import com.service.osiconnect.model.EmployeeRequestModel;
import com.service.osiconnect.repository.AttachmentRepository;
import com.service.osiconnect.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.service.osiconnect.constants.ApplicationConstants.ATTACH_LIST_EMPTY;
import static com.service.osiconnect.constants.ApplicationConstants.ERR_ATTACH_LIST_EMPTY;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;


    public List<AttachmentModel> getAttachments(Optional<EmployeeRequestModel> employeeRequestModel) {
        List<AttachmentModel> attachmentModelList = attachmentRepository.findByEmployeeRequest(employeeRequestModel);
        if (attachmentModelList == null || attachmentModelList.isEmpty()) {
            throw new ApplicationException(ATTACH_LIST_EMPTY, ERR_ATTACH_LIST_EMPTY, HttpStatus.BAD_REQUEST);
        }
        return attachmentModelList;
    }
}
