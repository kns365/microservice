package com.kns.apps.microservice.authserver.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentials {
    private String username, password;
}
