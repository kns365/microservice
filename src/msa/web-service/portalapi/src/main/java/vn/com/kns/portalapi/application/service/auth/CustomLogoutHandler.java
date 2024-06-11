package vn.com.kns.portalapi.application.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

//    @Autowired
//    private UserDetailsServiceImpl userService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextHolder.clearContext();
        try {
//            final UserDetails userEntity = userService.loadUserByUsername(authentication.getName());
            if (authentication != null) {
                log.info("Logged Out Handler: " + authentication.getName());
            } else {
                log.info("Authentication null");
            }
            //set status to false
//            userEntity.setStatus(false);
//            userService.saveUser(userEntity);
            //redirecting to another controller endpoint
//            response.sendRedirect("/auth/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
