package com.kns.apps.microservice.authservice.application.service.auth;

import com.kns.apps.microservice.authservice.core.entity.RefreshToken;
import com.kns.apps.microservice.authservice.data.repository.RefreshTokenRepository;
import com.kns.apps.microservice.authservice.security.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository tokenRefreshRepository;

    @Value("${jwt.expiration.time-refresh}")
    private Long jwtRefreshExpirationInMillis;

    @PostConstruct
    public void init() {
    }

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken tokenRefresh = new RefreshToken();
        tokenRefresh.setRefreshToken(UUID.randomUUID().toString());
        tokenRefresh.setUsername(username);
//        tokenRefresh.setExpiryDate(Date.from(Instant.now().plusMillis(Long.parseLong(String.valueOf(jwtExpirationRefreshInMillis)))));
        tokenRefresh.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationInMillis));
//        tokenRefresh.setCreatedDate(Date.from(Instant.now()));
        return tokenRefreshRepository.save(tokenRefresh);
    }

    void validateRefreshToken(String username, String token) throws Exception {
        RefreshToken tokenRefresh = tokenRefreshRepository.findByUsernameAndRefreshToken(username, token).orElseThrow(() -> new BaseException("Invalid refresh token"));
        if (Instant.now().isAfter(tokenRefresh.getExpiryDate())) {
            deleteRefreshTokenById(tokenRefresh.getId());
            throw new BaseException("Refresh token has expired");
        }
    }

    public void deleteRefreshToken(String token) {
        tokenRefreshRepository.deleteByRefreshToken(token);
    }

    public void deleteAllRefreshTokenByUsername(String username) {
        tokenRefreshRepository.deleteAllByUsername(username);
    }

    public void deleteRefreshTokenById(Long id) {
        tokenRefreshRepository.deleteById(id);
    }
}
