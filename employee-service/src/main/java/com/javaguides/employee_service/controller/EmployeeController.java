package com.javaguides.employee_service.controller;

import com.javaguides.employee_service.dto.EmployeeDto;
import com.javaguides.employee_service.dto.EmployeeResponse;
import com.javaguides.employee_service.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create-employee")
    ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        return  new ResponseEntity<>(employeeService.addEmployee(employeeDto),HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id){
        return new ResponseEntity<>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<List<EmployeeDto>> getEmployees(){
        return new ResponseEntity<>(employeeService.getEmployees(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable long id, @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployeeById(id,employeeDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable long id){
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id),HttpStatus.OK);
    }

    @GetMapping("/dept/{id}")
    ResponseEntity<EmployeeResponse> getEmployeeWithDepartment(@PathVariable long id){
        return new ResponseEntity<>(employeeService.getEmployeeWithDepartment(id),HttpStatus.OK);
    }
}
