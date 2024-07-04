package com.kns.apps.msa.gatewaylservice.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Author: KhoaNguyen
 * DateTime: 7/4/2024 5:22 PM
 */
@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = Arrays.asList(
            "/auth/register",
            "/auth/getToken"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
