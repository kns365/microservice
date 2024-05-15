package com.kns.apps.microservice.authservice.application.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenInput {
    @NotEmpty(message = "Required")
    private String username;
    @NotEmpty(message = "Required")
    private String refreshToken;
}
