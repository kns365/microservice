package vn.com.kns.portalapi.application.service.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String username;
    private String token;
    private String refreshToken;
    private Date expiresAt;

    public AuthenticationResponse(String username, String token) {
        this.token = token;
        this.username = username;
    }
}
