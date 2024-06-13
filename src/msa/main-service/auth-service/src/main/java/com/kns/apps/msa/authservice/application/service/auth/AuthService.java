package com.kns.apps.msa.authservice.application.service.auth;

import com.kns.apps.msa.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.msa.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.msa.configservice.security.JwtDto;
import org.springframework.security.core.Authentication;


public interface AuthService {

    JwtDto getToken(GetTokenInput input) throws Exception;

    JwtDto refreshToken(RefreshTokenInput input) throws Exception;

    void clearToken(Authentication authentication);
}
