package vn.com.kns.portalapi.data.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.kns.portalapi.core.entity.app.TokenRefresh;

import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<TokenRefresh, Long> {
    Optional<TokenRefresh> findByUsernameAndToken(String token, String username);
    void deleteByToken(String token);
    void deleteAllByUsername(String username);
}
