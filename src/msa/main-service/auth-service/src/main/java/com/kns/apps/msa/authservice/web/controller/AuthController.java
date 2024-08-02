package com.kns.apps.msa.authservice.web.controller;

import com.kns.apps.msa.authservice.application.service.auth.AuthService;
import com.kns.apps.msa.authservice.application.service.auth.dto.GetTokenInput;
import com.kns.apps.msa.authservice.application.service.auth.dto.RefreshTokenInput;
import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/getToken")
    public ResponseEntity<ResponseDto> getToken(@Valid @RequestBody GetTokenInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, authService.getToken(input));
        } catch (Exception e) {
            log.error("Error getToken {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto> refreshToken(@Valid @RequestBody RefreshTokenInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, authService.refreshToken(input));
        } catch (Exception e) {
            log.error("Error refreshToken: {}", e.getMessage());
            res = new ResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping("/clearToken")
    public ResponseEntity<ResponseDto> clearToken(Authentication input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            authService.clearToken(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error clearToken: {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
