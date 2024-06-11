package vn.com.kns.portalapi.data.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.app.TokenVerify;

import java.util.Optional;

@Repository
public interface TokenVerifyRepository extends JpaRepository<TokenVerify, Long> {
    Optional<TokenVerify> findByToken(String token);
    void deleteByToken(String token);
}
