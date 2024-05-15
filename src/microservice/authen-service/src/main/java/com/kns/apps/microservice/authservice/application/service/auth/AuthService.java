package com.kns.apps.microservice.authservice.application.service.auth;

import com.kns.apps.microservice.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.microservice.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.microservice.authservice.core.entity.RefreshToken;
import com.kns.apps.microservice.authservice.core.model.JwtDto;
import com.kns.apps.microservice.authservice.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtDto getToken(GetTokenInput input) throws Exception {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        JwtDto getToken = jwtProvider.generateToken(auth);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(input.getUsername());
        return new JwtDto(getToken, refreshToken.getRefreshToken());
    }

    public JwtDto refreshToken(RefreshTokenInput input) throws Exception {
        refreshTokenService.validateRefreshToken(input.getUsername(), input.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(input.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);

        JwtDto getToken = jwtProvider.generateToken(auth);
        return new JwtDto(getToken, input.getRefreshToken());
    }

}
