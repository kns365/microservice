package com.kns.apps.microservice.authservice.core.model;

import lombok.*;

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
