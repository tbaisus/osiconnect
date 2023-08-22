package com.osiconnect.repository;

import com.osiconnect.model.EmployeeModel;
import com.osiconnect.model.EmployeeRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequestModel, Long>{

    List<EmployeeRequestModel> findByEmployee(EmployeeModel employee);

    List<EmployeeRequestModel> findByEmployeeAndStatus(EmployeeModel employee,String status);

    List<EmployeeRequestModel> findByEmployeeAndDepartment(EmployeeModel employee,String status);

}

