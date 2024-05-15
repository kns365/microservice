package com.kns.apps.microservice.authserver.security;

import com.kns.apps.microservice.authserver.core.entity.RefreshToken;
import com.kns.apps.microservice.authserver.core.model.JwtDto;
import com.kns.apps.microservice.authserver.core.model.RefreshTokenRequest;
import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import com.kns.apps.microservice.configserver.security.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    @Autowired
    @Lazy
    private JwtConfig jwtConfig;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


//    public ResponseDto login(LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
////        String refreshToken = tokenRefreshService.generateRefreshToken(loginRequest.getUsername()).getToken();
//        log.info("Login: {}", loginRequest.getUsername());
//        return new ResponseDto(loginRequest.getUsername(), token, "", Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
//    }

    private String generateToken(Authentication auth) {
        Long now = System.currentTimeMillis();
        Date iat = new java.sql.Date(now);
        Date exp = new java.sql.Date(now + jwtConfig.getExpiration() * 1000);// in milliseconds
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        return token;
    }

    public ResponseDto refreshToken(RefreshTokenRequest tokenRefreshRequest) throws Exception {
        refreshTokenService.validateRefreshToken(tokenRefreshRequest.getUsername(), tokenRefreshRequest.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRefreshRequest.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = generateToken(authentication);
//        return new ResponseDto(tokenRefreshRequest.getUsername(), token, tokenRefreshRequest.getRefreshToken(), Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, token);
        return res;
    }

    public JwtDto refreshToken2(RefreshTokenRequest tokenRefreshRequest) throws Exception {
        refreshTokenService.validateRefreshToken(tokenRefreshRequest.getUsername(), tokenRefreshRequest.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRefreshRequest.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return generateJwt(auth);
    }

    private JwtDto generateJwt(Authentication auth) {
        Long now = System.currentTimeMillis();
        Date iat = new java.sql.Date(now);
        Date exp = new java.sql.Date(now + jwtConfig.getExpiration() * 1000);// in milliseconds
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(auth.getName());
        return new JwtDto(token, jwtConfig.getPrefix(), String.valueOf(exp.getTime()), refreshToken.getRefreshToken());
    }

}
