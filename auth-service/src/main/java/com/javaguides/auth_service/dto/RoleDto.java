package com.javaguides.auth_service.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String roleId;
    private String roleName;
}
