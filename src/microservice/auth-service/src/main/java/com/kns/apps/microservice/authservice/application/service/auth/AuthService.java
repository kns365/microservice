package com.kns.apps.microservice.authservice.application.service.auth;

import com.kns.apps.microservice.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.microservice.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.microservice.authservice.core.entity.RefreshToken;
import com.kns.apps.microservice.authservice.data.repository.RefreshTokenRepository;
import com.kns.apps.microservice.configserver.security.BaseException;
import com.kns.apps.microservice.configserver.security.JwtDto;
import com.kns.apps.microservice.configserver.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


public interface AuthService {

    JwtDto getToken(GetTokenInput input) throws Exception;

    JwtDto refreshToken(RefreshTokenInput input) throws Exception;

}
