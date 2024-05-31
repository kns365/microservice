//package com.kns.apps.microservice.galleryservice.config;
//
//import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HystrixConfig {
//
//    @Bean
//    public ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServlet() {
//        ServletRegistrationBean<HystrixMetricsStreamServlet> registration = new ServletRegistrationBean<>(new HystrixMetricsStreamServlet(), "/hystrix.stream");
//        registration.setName("HystrixMetricsStreamServlet");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//}
