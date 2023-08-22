package com.osiconnect.repository;

import com.osiconnect.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<EmployeeModel,Long> {




}
