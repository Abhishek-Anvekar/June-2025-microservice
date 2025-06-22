package com.javaguides.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaguides.auth_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private Set<Role> roles = new HashSet<>();
}
