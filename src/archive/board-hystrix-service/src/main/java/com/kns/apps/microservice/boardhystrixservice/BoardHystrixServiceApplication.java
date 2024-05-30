package com.kns.apps.microservice.boardhystrixservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableEurekaClient
//@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbineStream
public class BoardHystrixServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardHystrixServiceApplication.class, args);
    }

}
