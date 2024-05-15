package com.kns.apps.microservice.authservice.data.repository;

import com.kns.apps.microservice.authservice.core.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsernameAndRefreshToken(String token, String username);
    void deleteByRefreshToken(String token);
    void deleteAllByUsername(String username);
}
