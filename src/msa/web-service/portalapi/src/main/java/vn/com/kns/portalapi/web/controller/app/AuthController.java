package vn.com.kns.portalapi.web.controller.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.auth.AuthService;
import vn.com.kns.portalapi.application.service.auth.AuthenticationResponse;
import vn.com.kns.portalapi.application.service.auth.TokenRefreshService;
import vn.com.kns.portalapi.application.service.auth.dto.LoginRequest;
import vn.com.kns.portalapi.application.service.auth.dto.TokenRefreshRequest;
import vn.com.kns.portalapi.application.service.auth.dto.RegisterRequest;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenRefreshService tokenRefreshService;

    @Autowired
    private HttpServletRequest httpServletRequest;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest) {
//        authService.signup(registerRequest);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @GetMapping("/verifyAccount/{token}")
//    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws Exception {
//        try {
//            authService.verifyAccount(token);
//        } catch (Exception e) {
//            log.error("Error verifyAccount: {}", e.getMessage());
//            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Account activated successully", HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
//        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity<?> logoutGet(Authentication authentication) {
//        authService.logout(authentication);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PostMapping("/refreshToken")
//    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) throws Exception {
//        try {
//            return new ResponseEntity<>(authService.refreshToken(tokenRefreshRequest), HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Error refreshToken: {}", e.getMessage());
//            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
//        }
//    }

}
