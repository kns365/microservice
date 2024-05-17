package com.kns.apps.microservice.authservice.security;

import com.kns.apps.microservice.authservice.application.service.auth.UserDetailsServiceImpl;
import com.kns.apps.microservice.configserver.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Exception exception = null;
//        Date execDurStart = new Date();
//        HttpServletRequest resquestCacheWrapper = new MultiReadRequestWrapper((HttpServletRequest) request);
//        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                String username = jwtProvider.getUsernameFromJWT(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Token invalid");
//            exception = ex;
        }
        filterChain.doFilter(request, response);
//        try {
//            filterChain.doFilter(resquestCacheWrapper, responseCopier);
//            responseCopier.flushBuffer();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error(HttpStatus.INTERNAL_SERVER_ERROR.name());
//            exception = ex;
//        } finally {
//            AuditLogHelper.create(resquestCacheWrapper, responseCopier, execDurStart, exception);
//        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProvider.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProvider.getPrefix())) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
