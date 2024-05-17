package com.kns.apps.microservice.zuulserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/indexZuul")
    public String indexZuul() {
        return "Welcome to Zuul server.";
    }
}
