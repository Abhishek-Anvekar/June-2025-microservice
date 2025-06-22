package com.javaguides.department_service.service;

import com.javaguides.department_service.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto addDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentById(long id);
    List<DepartmentDto> getDepartments();
    DepartmentDto updateDepartmentById(long id,DepartmentDto departmentDto);
    String deleteDepartmentById(long id);

    DepartmentDto getDepartmentByDeptCode(String deptCode);
}
