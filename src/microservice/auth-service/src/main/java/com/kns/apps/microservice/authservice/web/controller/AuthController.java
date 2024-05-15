package com.kns.apps.microservice.authservice.web.controller;

import com.kns.apps.microservice.authservice.application.service.auth.AuthService;
import com.kns.apps.microservice.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.microservice.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/getToken")
    public ResponseEntity<ResponseDto> getToken(@Valid @RequestBody GetTokenInput input) {
        ResponseDto res = new ResponseDto();
        try {
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, authService.getToken(input));
        } catch (Exception e) {
            log.error("Error getToken {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto> refreshToken(@Valid @RequestBody RefreshTokenInput input) {
        ResponseDto res = new ResponseDto();
        try {
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, authService.refreshToken(input));
        } catch (Exception e) {
            log.error("Error refreshToken: {}", e.getMessage());
            res = new ResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
