package vn.com.kns.portalapi.application.service.auth.dto;

import lombok.Data;
import vn.com.kns.portalapi.core.entity.app.Role;

import java.util.List;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
//    private List<Role> roles;
}
