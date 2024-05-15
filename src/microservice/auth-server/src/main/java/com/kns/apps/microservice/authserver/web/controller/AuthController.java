package com.kns.apps.microservice.authserver.web.controller;

import com.kns.apps.microservice.authserver.core.model.JwtDto;
import com.kns.apps.microservice.authserver.core.model.RefreshTokenRequest;
import com.kns.apps.microservice.authserver.security.AuthService;
import com.kns.apps.microservice.authserver.security.RefreshTokenService;
import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private HttpServletRequest httpServletRequest;

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
//        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
//    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        ResponseDto res = new ResponseDto();
        try {
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, authService.refreshToken2(refreshTokenRequest));
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error refreshToken: {}", e.getMessage());
            res = new ResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null, null);
            return new ResponseEntity(res, HttpStatus.OK);
        }
    }

}
