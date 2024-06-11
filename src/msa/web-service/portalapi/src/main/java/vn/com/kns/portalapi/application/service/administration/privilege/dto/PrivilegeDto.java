package vn.com.kns.portalapi.application.service.administration.privilege.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto extends BaseEntityDto {
    private String name;
}
