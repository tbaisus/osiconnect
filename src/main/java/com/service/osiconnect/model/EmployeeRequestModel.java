package com.service.osiconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "EmployeeRequests")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EmployeeRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;
    @Column(name = "sla")
    private String sla = "not set";
    @Column(name = "description")
    private String description;
    @Column(name = "priority")
    private String priority;
    @Column(name = "status")
    private String status = "opened";
    @Column(name = "department")
    private String department;
    @Column(name = "requested_date")
    private LocalDateTime requestedDate;
    @Column(name = "category")
    private String category;
    @Column(name = "subcategory")
    private String subCategory;
    @Column(name = "expected_resolution_date")
    private LocalDate expectedResolutionDate;
    @Column(name = "summary")
    private String summary;
    @Column(name = "severity")
    private String severity;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private EmployeeModel employee;
    @OneToMany(mappedBy = "employeeRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachmentModel> attachments = new ArrayList<>();


}
