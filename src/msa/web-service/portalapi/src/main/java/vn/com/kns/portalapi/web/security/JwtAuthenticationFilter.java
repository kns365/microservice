package vn.com.kns.portalapi.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import vn.com.kns.portalapi.application.helper.AuditLogHelper;
import vn.com.kns.portalapi.application.service.auth.UserDetailsServiceImpl;
import vn.com.kns.portalapi.web.security.handler.HttpServletResponseCopier;
import vn.com.kns.portalapi.web.security.handler.MultiReadRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Date execDurStart = new Date();
        Exception exception = null;
        HttpServletRequest resquestCacheWrapper = new MultiReadRequestWrapper((HttpServletRequest) request);
        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
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
            exception = ex;
        }
        try {
            filterChain.doFilter(resquestCacheWrapper, responseCopier);
            responseCopier.flushBuffer();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(HttpStatus.INTERNAL_SERVER_ERROR.name());
            exception = ex;
        } finally {
            AuditLogHelper.create(resquestCacheWrapper, responseCopier, execDurStart, exception);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
