package com.kns.apps.microservice.authservice.application.service.auth;

import com.kns.apps.microservice.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.microservice.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.microservice.configserver.security.JwtDto;

public interface AuthService {
    JwtDto getToken(GetTokenInput input) throws Exception;

    JwtDto refreshToken(RefreshTokenInput input) throws Exception;
}
