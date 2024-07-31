package com.kns.apps.msa.logservice.security;

import com.kns.apps.msa.commonpack.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/admin/login", "/admin/logout", "/actuator/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/wallboard", true)
//                .loginProcessingUrl("/login") // Submit URL
//                .usernameParameter("username")
//                .passwordParameter("password")

                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                .and()
                .rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(Math.toIntExact(jwtProvider().getJwtExpirationInMillis()) / 1000)
        ;
    }


}
