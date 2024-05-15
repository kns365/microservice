package com.kns.apps.microservice.authservice.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {
    private String username, password;
}
