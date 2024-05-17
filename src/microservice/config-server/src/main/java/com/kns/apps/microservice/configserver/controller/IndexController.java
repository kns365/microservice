package com.kns.apps.microservice.configserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("/indexConfig")
    public String indexConfig() {
        return "Welcome to Configuration server.";
    }

}
