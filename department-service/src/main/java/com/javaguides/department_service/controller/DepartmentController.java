package com.javaguides.department_service.controller;

import com.javaguides.department_service.dto.DepartmentDto;
import com.javaguides.department_service.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create-department")
    ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.addDepartment(departmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable long id){
        return new ResponseEntity<>(departmentService.getDepartmentById(id),HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<List<DepartmentDto>> getDepartments(){
        return new ResponseEntity<>(departmentService.getDepartments(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<DepartmentDto> updateDepartmentById(@PathVariable long id, @RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.updateDepartmentById(id,departmentDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> updateDepartmentById(@PathVariable long id){
        return new ResponseEntity<>(departmentService.deleteDepartmentById(id),HttpStatus.OK);
    }

    @GetMapping("/dept-code/{deptCode}")
    ResponseEntity<DepartmentDto> getDepartmentByDeptCode(@PathVariable String deptCode){
        return new ResponseEntity<>(departmentService.getDepartmentByDeptCode(deptCode),HttpStatus.OK);
    }

}
