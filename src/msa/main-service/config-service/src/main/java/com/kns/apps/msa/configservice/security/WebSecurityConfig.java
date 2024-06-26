package com.kns.apps.msa.configservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().and()// Use Basic Authentication
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()

//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/", true)
//
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")

//                .and()
//                .rememberMe().tokenRepository(this.persistentTokenRepository())
//                .tokenValiditySeconds(Math.toIntExact(jwtProvider().getJwtExpirationInMillis()) / 1000)
        ;
    }
}
