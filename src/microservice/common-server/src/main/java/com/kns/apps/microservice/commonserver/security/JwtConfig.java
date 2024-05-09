package com.kns.apps.microservice.commonserver.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

// To use this class outside. You have to 
// 1. Define it as a bean, either by adding @Component or use @Bean to instantiate an object from it
// 2. Use the @Autowire to ask spring to auto create it for you, and inject all the values.

// So, If you tried to create an instance manually (i.e. new JwtConfig()). This won't inject all the values. 
// Because you didn't ask Spring to do so (it's done by you manually!).
// Also, if, at any time, you tried to instantiate an object that's not defined as a bean
// Don't expect Spring will autowire the fields inside that class object.

@Getter        // lombok will create getters auto.
@ToString        // [IMP] You need to install lombok jar file: https://stackoverflow.com/a/11807022
public class JwtConfig {

    // Spring doesn't inject/autowire to "static" fields.
    // Link: https://stackoverflow.com/a/6897406
    @Value("${security.jwt.uri:/auth/**}")
//    @Value("${security.jwt.uri}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
//    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
//    @Value("${security.jwt.prefix}")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
//    @Value("${security.jwt.expiration}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
//    @Value("${security.jwt.secret}")
    private String secret;

    // In case you want to use plain getters instead of lombok.
    /*public String getUri() {
        return Uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }*/

}
