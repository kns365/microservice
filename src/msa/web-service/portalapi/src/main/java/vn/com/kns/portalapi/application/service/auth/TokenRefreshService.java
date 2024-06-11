package vn.com.kns.portalapi.application.service.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.core.entity.app.TokenRefresh;
import vn.com.kns.portalapi.data.repository.app.TokenRefreshRepository;
import vn.com.kns.portalapi.web.exception.BaseException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class TokenRefreshService {

    @Autowired
    private TokenRefreshRepository tokenRefreshRepository;

    @Value("${jwt.expiration.time-refresh}")
    private Long jwtRefreshExpirationInMillis;

    @PostConstruct
    public void init() { }

    public TokenRefresh generateRefreshToken(String username) {
        TokenRefresh tokenRefresh = new TokenRefresh();
        tokenRefresh.setToken(UUID.randomUUID().toString());
        tokenRefresh.setUsername(username);
//        tokenRefresh.setExpiryDate(Date.from(Instant.now().plusMillis(Long.parseLong(String.valueOf(jwtExpirationRefreshInMillis)))));
        tokenRefresh.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationInMillis));
//        tokenRefresh.setCreatedDate(Date.from(Instant.now()));
        return tokenRefreshRepository.save(tokenRefresh);
    }

    void validateRefreshToken(String username, String token) throws Exception {
        TokenRefresh tokenRefresh = tokenRefreshRepository.findByUsernameAndToken(username, token).orElseThrow(() -> new BaseException("Invalid token refresh"));
        if (Instant.now().isAfter(tokenRefresh.getExpiryDate())) {
            deleteRefreshTokenById(tokenRefresh.getId());
            throw new BaseException("Refresh token has expired");
        }
    }

    public void deleteRefreshToken(String token) {
        tokenRefreshRepository.deleteByToken(token);
    }

    public void deleteAllRefreshTokenByUsername(String username) {
        tokenRefreshRepository.deleteAllByUsername(username);
    }

    public void deleteRefreshTokenById(Long id) {
        tokenRefreshRepository.deleteById(id);
    }
}
