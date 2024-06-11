package vn.com.kns.portalapi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @GetMapping("/")
    public String indexPage(){
        return "Welcome to Portal API.";
    }
}
