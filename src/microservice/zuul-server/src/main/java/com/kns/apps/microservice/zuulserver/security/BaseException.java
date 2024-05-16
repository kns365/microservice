package com.kns.apps.microservice.zuulserver.security;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
