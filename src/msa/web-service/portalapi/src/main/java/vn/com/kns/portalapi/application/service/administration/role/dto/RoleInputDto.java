package vn.com.kns.portalapi.application.service.administration.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInputDto {
    private Long id;
    private String name;
//    private List<PrivilegeInputDto> privileges;
    private List<String> privilegesString;
}
