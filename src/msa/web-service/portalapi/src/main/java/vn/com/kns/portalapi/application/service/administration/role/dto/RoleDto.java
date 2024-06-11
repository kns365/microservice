package vn.com.kns.portalapi.application.service.administration.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseEntityDto {
    private String name;
    private List<PrivilegeDto> privileges;
    private List<String> privilegesString;
}
