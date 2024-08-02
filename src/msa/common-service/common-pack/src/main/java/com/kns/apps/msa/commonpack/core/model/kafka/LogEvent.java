package com.kns.apps.msa.commonpack.core.model.kafka;

import com.kns.apps.msa.commonpack.application.helper.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public LogEvent(String serviceName, String clientIpAddress, String clientName, String browserInfo, String path, String httpStatus, String exception, String input, String output, Date execDurStart) {
        this.serviceName = serviceName;
        this.clientIpAddress = clientIpAddress;
        this.clientName = clientName;
        this.browserInfo = browserInfo;
        this.path = path;
        this.httpStatus = httpStatus;
        this.exception = exception;
        this.input = input;
        this.output = output;
        this.execDuration = DateHelper.getDuration(execDurStart, new Date(), TimeUnit.MILLISECONDS);
    }
}
