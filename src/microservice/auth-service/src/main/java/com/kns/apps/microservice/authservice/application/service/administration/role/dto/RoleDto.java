package com.kns.apps.microservice.authservice.application.service.administration.role.dto;

import com.kns.apps.microservice.authservice.application.service.administration.privilege.dto.PrivilegeDto;
import com.kns.apps.microservice.configserver.core.model.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseEntityDto {
    private String name;
    private List<PrivilegeDto> privileges;
    private List<String> privilegesString;
}
