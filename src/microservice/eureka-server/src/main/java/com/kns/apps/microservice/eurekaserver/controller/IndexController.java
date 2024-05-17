package com.kns.apps.microservice.eurekaserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("/indexEureka")
    public String indexEureka() {
        return "Welcome to Eureka server.";
    }

}
