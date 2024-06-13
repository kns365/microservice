package com.kns.apps.msa.zuulservice.security;

import com.kns.apps.msa.configservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity    // Enable security config. This annotation denotes config for spring security.
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()//ensure use JwtWebMvcConfig
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(new JwtGwAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // authorization requests config
                .authorizeRequests()
                // allow all who are accessing "auth" service
                .antMatchers(jwtProvider.getUri()).permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/swagger", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .antMatchers("/gallery/v3/api-docs/**", "/order/v3/api-docs/**", "/image/v3/api-docs/**", "/portalapi/v3/api-docs/**").permitAll()
                // must be an admin if trying to access admin area (authentication is also required here)
                .antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
                // Any other request must be authenticated
                .anyRequest().authenticated();
    }


}
