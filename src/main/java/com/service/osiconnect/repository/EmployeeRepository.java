package com.service.osiconnect.repository;

import com.service.osiconnect.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<EmployeeModel,Long> {




}
