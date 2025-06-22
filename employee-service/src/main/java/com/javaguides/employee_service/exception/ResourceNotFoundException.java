package com.javaguides.employee_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldType;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldType, String fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldType,fieldValue));
        this.resourceName = resourceName;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }
}
