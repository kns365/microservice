package com.kns.apps.microservice.configserver.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private String expiresAt;
    private String refreshToken;

    public JwtDto(String accessToken, String tokenType, String expiresAt) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
    }

    public JwtDto(JwtDto jwt, String refreshToken) {
        this.accessToken = jwt.getAccessToken();
        this.tokenType = jwt.getTokenType();
        this.expiresAt = jwt.getExpiresAt();
        this.refreshToken = refreshToken;
    }
}
