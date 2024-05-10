package com.kns.apps.microservice.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient        // It acts as a eureka client
@EnableConfigServer
public class ConfigServerApplication {
//    DataSourceAutoConfiguration
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
