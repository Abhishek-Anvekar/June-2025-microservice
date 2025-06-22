package com.javaguides.employee_service.service.serviceImpl;

import com.javaguides.employee_service.dto.DepartmentDto;
import com.javaguides.employee_service.dto.EmployeeDto;
import com.javaguides.employee_service.dto.EmployeeResponse;
import com.javaguides.employee_service.entity.Employee;
import com.javaguides.employee_service.exception.ResourceNotFoundException;
import com.javaguides.employee_service.repository.EmployeeRepository;
import com.javaguides.employee_service.service.EmployeeService;
import com.javaguides.employee_service.service.client.DepartmentFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepo;
    private ModelMapper mapper;
    private DepartmentFeignClient departmentFeignClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo, ModelMapper mapper, DepartmentFeignClient departmentFeignClient) {
        this.employeeRepo = employeeRepo;
        this.mapper = mapper;
        this.departmentFeignClient = departmentFeignClient;
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepo.save(mapper.map(employeeDto, Employee.class));
        return mapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", String.valueOf(id))
        );

        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        return employeeRepo.findAll().stream()
                .map(employee -> mapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", String.valueOf(id))
        );
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartmentCode(employeeDto.getDepartmentCode());
        Employee updatedEmployee = employeeRepo.save(employee);
        return mapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public String deleteEmployeeById(long id) {
        employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", String.valueOf(id))
        );
        employeeRepo.deleteById(id);
        return "Employee deleted successfully.";
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
//    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public EmployeeResponse getEmployeeWithDepartment(long id) {
        LOGGER.info("Inside getEmployeeWithDepartmentById() method");
        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", String.valueOf(id))
        );

        DepartmentDto departmentDto = departmentFeignClient.getDepartmentByDeptCode(employee.getDepartmentCode()).getBody();

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(departmentDto)
                .build();
    }

    public EmployeeResponse getDefaultDepartment(long id,Exception e){
        LOGGER.info("Inside getDefaultDepartment() method");

        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", String.valueOf(id))
        );

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1);
        departmentDto.setName("Data Unavailable");
        departmentDto.setDescription("Data Unavailable");
        departmentDto.setDepartmentCode("Data Unavailable");

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(departmentDto)
                .build();

    }
}
