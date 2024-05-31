package com.kns.apps.msa.authservice.application.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenInput {
    @NotEmpty(message = "Required")
    private String username;
    @NotEmpty(message = "Required")
    private String password;
}
