package com.javaguides.department_service.service.serviceImpl;

import com.javaguides.department_service.dto.DepartmentDto;
import com.javaguides.department_service.entity.Department;
import com.javaguides.department_service.exception.ResourceNotFoundException;
import com.javaguides.department_service.repository.DepartmentRepository;
import com.javaguides.department_service.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepo;
    private ModelMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepo, ModelMapper mapper) {
        this.departmentRepo = departmentRepo;
        this.mapper = mapper;
    }

    @Override
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        Department savedDepartment = departmentRepo.save(mapper.map(departmentDto, Department.class));
        return mapper.map(savedDepartment,DepartmentDto.class);
    }

    @Override
    public DepartmentDto getDepartmentById(long id) {
        Department department = departmentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department not found with id : " + id)
        );

        return mapper.map(department,DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        return departmentRepo.findAll().stream()
                .map(department -> mapper.map(department,DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartmentById(long id, DepartmentDto departmentDto) {
        Department department = departmentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department not found with id : " + id)
        );
        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        Department updatedDepartment = departmentRepo.save(department);
        return mapper.map(updatedDepartment,DepartmentDto.class);
    }

    @Override
    public String deleteDepartmentById(long id) {
        Department department = departmentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department not found with id : " + id)
        );
        departmentRepo.deleteById(id);
        return "Department deleted successfully";
    }

    @Override
    public DepartmentDto getDepartmentByDeptCode(String deptCode) {
        Department department = departmentRepo.findByDepartmentCode(deptCode).orElseThrow(
                () -> new ResourceNotFoundException("Department not found with department code: " + deptCode)
        );
        return mapper.map(department,DepartmentDto.class);
    }
}
