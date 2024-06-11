package vn.com.kns.portalapi.application.service.administration.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.application.service.administration.role.dto.RoleDto;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

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
