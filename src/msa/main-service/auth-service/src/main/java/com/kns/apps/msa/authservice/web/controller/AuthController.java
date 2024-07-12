package com.kns.apps.msa.authservice.web.controller;

import com.kns.apps.msa.authservice.application.service.auth.AuthService;
import com.kns.apps.msa.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.msa.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
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

    @GetMapping("/clearToken")
    public ResponseEntity<ResponseDto> clearToken(Authentication authentication) {
        ResponseDto res = new ResponseDto();
        try {
            authService.clearToken(authentication);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error clearToken: {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
