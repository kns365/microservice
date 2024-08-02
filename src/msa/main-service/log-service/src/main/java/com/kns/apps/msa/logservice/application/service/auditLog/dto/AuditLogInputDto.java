package com.kns.apps.msa.logservice.application.service.auditLog.dto;

import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditLogInputDto {
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

    public AuditLogInputDto(LogEvent event) {
        this.serviceName = event.getServiceName();
        this.clientIpAddress = event.getClientIpAddress();
        this.clientName = event.getClientName();
        this.browserInfo = event.getBrowserInfo();
        this.path = event.getPath();
        this.httpStatus = event.getHttpStatus();
        this.exception = event.getException();
        this.input = event.getInput();
        this.output = event.getOutput();
        this.execDuration = event.getExecDuration();
    }
}
