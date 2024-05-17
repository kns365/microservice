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

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtDto getToken(GetTokenInput input) throws Exception {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
        JwtDto getToken = jwtProvider.generateToken(auth);
        RefreshToken refreshToken = generateRefreshToken(input.getUsername());
        return new JwtDto(getToken, refreshToken.getRefreshToken());
    }

    public JwtDto refreshToken(RefreshTokenInput input) throws Exception {
        validateRefreshToken(input.getUsername(), input.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(input.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        JwtDto getToken = jwtProvider.generateToken(auth);
        return new JwtDto(getToken, input.getRefreshToken());
    }

    private RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProvider.getJwtRefreshExpirationInMillis()));
        return refreshTokenRepository.save(refreshToken);
    }

    private void validateRefreshToken(String username, String token) throws Exception {
        RefreshToken refreshToken = refreshTokenRepository.findByUsernameAndRefreshToken(username, token).orElseThrow(() -> new BaseException("Invalid refresh token"));
        if (Instant.now().isAfter(refreshToken.getExpiryDate())) {
            deleteRefreshTokenById(refreshToken.getId());
            throw new BaseException("Refresh token has expired");
        }
    }

    private void deleteRefreshTokenById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    private void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByRefreshToken(token);
    }

    private void deleteAllRefreshTokenByUsername(String username) {
        refreshTokenRepository.deleteAllByUsername(username);
    }
}
