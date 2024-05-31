package com.kns.apps.msa.authservice.application.service.privilege.dto;

import com.kns.apps.msa.configservice.core.model.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto extends BaseEntityDto {
    private String name;
}
