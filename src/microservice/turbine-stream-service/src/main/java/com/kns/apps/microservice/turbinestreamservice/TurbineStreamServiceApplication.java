package com.kns.apps.microservice.turbinestreamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableEurekaClient
@EnableTurbineStream
public class TurbineStreamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineStreamServiceApplication.class, args);
    }

}
