//package com.kns.apps.msa.gatewaylservice.security;
//
//import com.kns.apps.msa.configservice.security.JwtProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class JwtWebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    @Lazy
//    private JwtProvider jwtProvider;
//
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**") // Cho phép tất cả các URL
//                .allowedOrigins("*") //.allowedOrigins("http://localhost:3000") // Cho phép nguồn gốc cụ thể
//                .allowedMethods("*") //.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Các phương thức HTTP được phép
//                .allowedHeaders("*") // Các header được phép
//                .exposedHeaders(jwtProvider.getHeader())
//                .allowCredentials(true) // Cho phép gửi thông tin đăng nhập (cookies)
//                .maxAge(3600L); // Thời gian mà trình duyệt có thể lưu trữ thông tin này
//    }
//}
