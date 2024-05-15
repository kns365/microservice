package com.kns.apps.microservice.authservice.security.exception;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
