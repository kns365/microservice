package com.kns.apps.msa.commonpack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// To use this class outside. You have to
// 1. Define it as a bean, either by adding @Component or use @Bean to instantiate an object from it
// 2. Use the @Autowire to ask spring to auto create it for you, and inject all the values.

// So, If you tried to create an instance manually (i.e. new JwtConfig()). This won't inject all the values.
// Because you didn't ask Spring to do so (it's done by you manually!).
// Also, if, at any time, you tried to instantiate an object that's not defined as a bean
// Don't expect Spring will autowire the fields inside that class object.

@Component
@Slf4j
@Getter
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.keystore.secret}")
    private String jwtSecretKey;

    @Value("${jwt.keystore.alias}")
    private String jwtAliasKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Value("${jwt.expiration.time-refresh}")
    private Long jwtRefreshExpirationInMillis;

    @Value("${jwt.uri}")
    private String Uri;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

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

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(this.jwtAliasKey, this.jwtSecretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new BaseException("Exception occured while retrieving public key from keystore");
        }
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate(this.jwtAliasKey).getPublicKey();
        } catch (KeyStoreException e) {
            throw new BaseException("Exception occured while retrieving public key from keystore");
        }
    }

    public JwtDto generateToken(Authentication auth) {
        Date iat = Date.from(Instant.now());
        Date exp = Date.from(Instant.now().plusMillis(Long.parseLong(String.valueOf(jwtExpirationInMillis))));
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(getPrivateKey())
                .compact();
        return new JwtDto(token, "Bearer", jwtExpirationInMillis.toString());
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt).getBody();
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
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
