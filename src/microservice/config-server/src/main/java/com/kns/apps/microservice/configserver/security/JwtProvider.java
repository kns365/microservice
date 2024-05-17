package com.kns.apps.microservice.configserver.security;

import com.kns.apps.microservice.configserver.application.helper.DateHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

@Component
@Slf4j
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.keystore.secret}")
    private String jwtSecretKey;

    @Value("${jwt.keystore.alias}")
    private String jwtAliasKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;


//    check tai sao keystore phải để ở zuul server thì mới chạy dc, ko thì JwtProvider bị null

//    public JwtProvider(){
//        try {
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/keystore/" + this.jwtAliasKey + ".jks");
//            keyStore.load(resourceAsStream, this.jwtSecretKey.toCharArray());
//        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
//            throw new BaseException("Exception occured while loading keystore");
//        }
//    }

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/keystore/" + this.jwtAliasKey + ".jks");
            keyStore.load(resourceAsStream, this.jwtSecretKey.toCharArray());
            log.info("keyStore loaded");
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new BaseException("Exception occured while loading keystore");
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
        return new JwtDto(token, "Bearer", DateHelper.getDateStr("yyyy-MM-dd HH:mm:ss", exp));
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(this.jwtAliasKey, this.jwtSecretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new BaseException("Exception occured while retrieving public key from keystore");
        }
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt).getBody();
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
