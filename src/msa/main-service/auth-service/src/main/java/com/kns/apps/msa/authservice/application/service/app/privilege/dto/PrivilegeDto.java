package com.kns.apps.msa.authservice.application.service.app.privilege.dto;

import com.kns.apps.msa.commonpack.core.model.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto extends BaseEntityDto {
    private String name;
}
