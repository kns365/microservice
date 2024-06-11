package vn.com.kns.portalapi.application.service.auth;

import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.administration.user.UserServiceImpl;
import vn.com.kns.portalapi.application.service.administration.user.dto.UserDto;
import vn.com.kns.portalapi.application.service.administration.user.dto.UserInputDto;
import vn.com.kns.portalapi.application.service.auth.dto.*;
import vn.com.kns.portalapi.core.entity.app.User;
import vn.com.kns.portalapi.data.repository.app.RoleRepository;
import vn.com.kns.portalapi.data.repository.app.UserRepository;
import vn.com.kns.portalapi.web.security.JwtProvider;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRefreshService tokenRefreshService;
    @Autowired
    private TokenVerifyService tokenVerifyService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(RegisterRequest input) {
        UserInputDto user = new UserInputDto(null, input.getUsername(), encodePassword(input.getPassword()), input.getFullName(), input.getEmail(), false, Arrays.asList("ROLE_ANONYMOUS"));
        UserDto userData = userService.createOrEdit(user);
        String token = tokenVerifyService.generateVerifyToken(userData.getId());
//        mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(), "Thank you for signing up to Spring Reddit, " + "please click on the below url to activate your account : " + "http://localhost:8080/api/auth/accountVerification/" + token));
        log.info("Please activate account '" + userData.getUsername() + "', click here: http://localhost:4320/auth/verifyAccount/" + token);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
//        String refreshToken = tokenRefreshService.generateRefreshToken(loginRequest.getUsername()).getToken();
        log.info("Login: {}", loginRequest.getUsername());
        return new AuthenticationResponse(loginRequest.getUsername(), token, "", Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
    }

    public void logout(Authentication authentication) {
        SecurityContextHolder.clearContext();
        try {
            if (authentication != null) {
                log.info("Logout: " + authentication.getName());
                tokenRefreshService.deleteAllRefreshTokenByUsername(authentication.getName());
            } else {
                log.info("Authentication null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    public AuthenticationResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) throws Exception {
        tokenRefreshService.validateRefreshToken(tokenRefreshRequest.getUsername(), tokenRefreshRequest.getRefreshToken());

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRefreshRequest.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(tokenRefreshRequest.getUsername(), token, tokenRefreshRequest.getRefreshToken(), Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
    }

    public void verifyAccount(String token) throws Exception {
        tokenVerifyService.verifyAccount(token);
    }
}
