package com.kns.apps.microservice.authservice.application.service.administration.user.dto;

import com.kns.apps.microservice.authservice.application.service.administration.role.dto.RoleDto;
import com.kns.apps.microservice.configserver.core.model.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseEntityDto {
    private String username;
    private String fullName;
    private String email;
    private boolean enabled;
    private List<RoleDto> roles;
    private List<String> rolesString;
}