package com.kns.apps.msa.authservice.application.service.role.dto;

import com.kns.apps.msa.authservice.application.service.privilege.dto.PrivilegeDto;
import com.kns.apps.msa.configservice.core.model.BaseEntityDto;
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
