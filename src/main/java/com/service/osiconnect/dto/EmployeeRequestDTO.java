package com.service.osiconnect.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
public class EmployeeRequestDTO {

  @NotEmpty
  private String summary = "  ";
  @NotBlank
  private String description;
  @NotBlank
  private String priority = "low";
 // @NotBlank
  private String department;
  @NotBlank
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Expected format: yyyy-MM-dd")
  private String expectedResolutionDate = "2023-10-02";
  @NotBlank
  private String category = "TBA";
  @NotBlank
  private String subCategory = "TBA";
  @NotBlank
  private String severity = "low";


}
