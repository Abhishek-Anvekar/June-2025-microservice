package com.javaguides.employee_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private DepartmentDto department;
}
