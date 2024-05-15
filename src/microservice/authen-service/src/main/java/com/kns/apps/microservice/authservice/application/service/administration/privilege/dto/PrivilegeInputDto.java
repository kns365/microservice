package com.kns.apps.microservice.authservice.application.service.administration.privilege.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeInputDto {
    private Long id;
    private String name;
    private Boolean isTemplate;
}
