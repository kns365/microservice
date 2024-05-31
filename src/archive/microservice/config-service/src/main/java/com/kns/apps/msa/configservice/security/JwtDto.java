package com.kns.apps.msa.configservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private String expiresIn;
//    private String expiresAt; //DateHelper.getDateStr("yyyy-MM-dd HH:mm:ss", exp)
    private String refreshToken;

    public JwtDto(String accessToken, String tokenType, String expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public JwtDto(JwtDto jwt, String refreshToken, String expiresIn) {
        this.accessToken = jwt.getAccessToken();
        this.tokenType = jwt.getTokenType();
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }
}
