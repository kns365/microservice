package com.kns.apps.microservice.authservice.application.service.privilege.dto;

import com.kns.apps.microservice.configserver.core.model.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto extends BaseEntityDto {
    private String name;
}
