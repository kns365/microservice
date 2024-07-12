package com.kns.apps.msa.authservice.web.security;

import com.kns.apps.msa.commonpack.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //for @PreAuthorize(...)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler()).and()
//                .exceptionHandling().accessDeniedHandler(forbiddenHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()// don't create session , session will be auto valid every request after first login
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/getToken", "/refreshToken").permitAll()
                .antMatchers(HttpMethod.GET, "/clearToken").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilterBean() {
//        return new JwtAuthenticationFilter();
//    }

//    @Bean
//    public UnauthorizedHandler unauthorizedHandler() throws Exception {
//        return new UnauthorizedHandler();
//    }
//
//    @Bean
//    public ForbiddenHandler forbiddenHandler() throws Exception {
//        return new ForbiddenHandler();
//    }
}
