package com.javaguides.employee_service.service;

import com.javaguides.employee_service.dto.EmployeeDto;
import com.javaguides.employee_service.dto.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(long id);
    List<EmployeeDto> getEmployees();
    EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto);
    String deleteEmployeeById(long id);

    EmployeeResponse getEmployeeWithDepartment(long id);
}
