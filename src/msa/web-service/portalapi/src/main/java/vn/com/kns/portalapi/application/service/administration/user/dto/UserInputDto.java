package vn.com.kns.portalapi.application.service.administration.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private boolean enabled;
//    private List<RoleInputDto> roles;
    private List<String> rolesString;
}
