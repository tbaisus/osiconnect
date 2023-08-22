package com.osiconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attachments")
@Data
public class AttachmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachmentId")
    private Long attachmentId;
    @Column(name = "filename")
    private String fileName;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "shared_by")
    private String sharedBy;
    @Column(name = "download_key")
    private String downloadKey;

    @ManyToOne
    @JoinColumn(name = "request_id")
    @JsonIgnore
    private EmployeeRequestModel employeeRequest;
}
