package com.service.osiconnect.repository;

import com.service.osiconnect.model.AttachmentModel;
import com.service.osiconnect.model.EmployeeRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentModel, Long> {

    List<AttachmentModel> findByEmployeeRequest(Optional<EmployeeRequestModel> employeeRequestModel);
}
