package com.kns.apps.microservice.authserver.security;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kns.apps.microservice.authserver.core.model.JwtDto;
import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import com.kns.apps.microservice.configserver.helper.JsonHelper;
import com.kns.apps.microservice.configserver.security.JwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // We use auth manager to validate the user credentials
    private AuthenticationManager authManager;

    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 1. Get credentials from request
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            // 2. Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), Collections.emptyList());

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authManager.authenticate(authToken);
        } catch (IOException e) {
            response.addHeader(jwtConfig.getHeader(), String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
            throw new RuntimeException(e);
        }
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Long now = System.currentTimeMillis();
            Date iat = new Date(now);
            Date exp = new Date(now + jwtConfig.getExpiration() * 1000);// in milliseconds
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
            JwtDto jwt = new JwtDto(token, jwtConfig.getPrefix(), String.valueOf(exp.getTime()), "");

            // Add token to header
            response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

            // Add token to body
            ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), jwt);
            response.getWriter().write(JsonHelper.objectToJson(res));
        } catch (IOException io) {
            try {
                ResponseDto res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), io.getMessage(), null);
                response.getWriter().write(JsonHelper.objectToJson(res));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto res = new ResponseDto(HttpStatus.UNAUTHORIZED.value(), failed.getMessage(), null);
        try {
            response.getWriter().write(JsonHelper.objectToJson(res));
        } catch (IOException io) {
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), io.getMessage(), null);
            response.getWriter().write(JsonHelper.objectToJson(res));
        }
    }

    // A (temporary) class just to represent the user credentials
    @Getter
    @Setter
    public static class UserCredentials {
        private String username, password;
    }
}
