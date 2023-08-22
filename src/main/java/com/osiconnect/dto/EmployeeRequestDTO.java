package com.osiconnect.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Data
public class EmployeeRequestDTO {

    @NotBlank
    private String summary;

    private String description;
    @NotBlank
    private String priority;
    @NotBlank
    private String department;
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Expected format: yyyy-MM-dd")
    private String expectedResolutionDate;
    @NotBlank
    private String category;
    @NotBlank
    private String subCategory;
    @NotBlank
    private String severity;


}
