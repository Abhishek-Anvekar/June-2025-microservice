package com.javaguides.employee_service.service.client;

import com.javaguides.employee_service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8080", value = "DEPARTMENT-SERVICE") // before implementing eureka server
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentFeignClient {

    @GetMapping("/api/departments/dept-code/{deptCode}")
    ResponseEntity<DepartmentDto> getDepartmentByDeptCode(@PathVariable String deptCode);
}
