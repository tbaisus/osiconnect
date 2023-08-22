package com.osiconnect.repository;

import com.osiconnect.model.AttachmentModel;
import com.osiconnect.model.EmployeeRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentModel, Long> {

    List<AttachmentModel> findByEmployeeRequest(Optional<EmployeeRequestModel> employeeRequestModel);
}
