package com.kns.apps.msa.commonpack.core.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {
    private Long id;
    private String serviceName;
    private String clientName;
    private Long execDuration;
    private String clientIpAddress;
    private String path;
    private String httpStatus;
    private String exception;
    private String browserInfo;
    private String input;
    private String output;
}
