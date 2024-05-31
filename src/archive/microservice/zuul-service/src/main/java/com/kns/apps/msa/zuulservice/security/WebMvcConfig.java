package com.kns.apps.msa.zuulservice.security;

import com.kns.apps.msa.configservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    @Lazy
    private JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")//.allowedOriginPatterns("*")
                .allowedMethods("*")//.allowedMethods("GET, POST, PUT, DELETE")
                .maxAge(3600L)
                .allowedHeaders("*")
                .exposedHeaders(jwtProvider.getHeader())
                .allowCredentials(true);
    }


}
