package com.kns.apps.microservice.authservice.security;

import com.kns.apps.microservice.authservice.core.entity.RefreshToken;
import com.kns.apps.microservice.authservice.data.repository.RefreshTokenRepository;
import com.kns.apps.microservice.authservice.security.exception.BaseException;
import com.kns.apps.microservice.configserver.security.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    @Lazy
    private JwtConfig jwtConfig;

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUsername(username);
//        refreshToken.setExpiryDate(Date.from(Instant.now().plusMillis(Long.parseLong(String.valueOf(jwtExpirationRefreshInMillis)))));
        refreshToken.setExpiryDate(Instant.now().plusSeconds(jwtConfig.getExpiration() * 2));
        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String username, String token) throws Exception {
        RefreshToken refreshToken = refreshTokenRepository.findByUsernameAndRefreshToken(username, token).orElseThrow(() -> new BaseException("Invalid refresh token"));
        if (Instant.now().isAfter(refreshToken.getExpiryDate())) {
            deleteRefreshTokenById(refreshToken.getId());
            throw new BaseException("Refresh token has expired");
        }
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByRefreshToken(token);
    }

    public void deleteAllRefreshTokenByUsername(String username) {
        refreshTokenRepository.deleteAllByUsername(username);
    }

    public void deleteRefreshTokenById(Long id) {
        refreshTokenRepository.deleteById(id);
    }
}
