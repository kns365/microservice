package com.kns.apps.microservice.authserver.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
}
