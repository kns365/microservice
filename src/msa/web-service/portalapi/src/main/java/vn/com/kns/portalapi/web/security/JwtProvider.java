package vn.com.kns.portalapi.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import vn.com.kns.portalapi.web.exception.BaseException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.keystore.secret}")
    private String jwtSecretKey;

    @Value("${jwt.keystore.alias}")
    private String jwtAliasKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/keystore/" + this.jwtAliasKey + ".jks");
            keyStore.load(resourceAsStream, this.jwtSecretKey.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new BaseException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        List<String> listRole = principal.getAuthorities().stream().map(p -> p.getAuthority()).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("roles", listRole)
                .claim("role", listRole)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(String.valueOf(jwtExpirationInMillis)))))
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(this.jwtAliasKey, this.jwtSecretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new BaseException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate(this.jwtAliasKey).getPublicKey();
        } catch (KeyStoreException e) {
            throw new BaseException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<String> getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = new ArrayList<>();
        roles = claims.get("roles", roles.getClass());

        return roles;
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
