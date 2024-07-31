package vn.com.kns.portalapi.application.service.administration.auditLog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.com.kns.portalapi.application.helper.DateHelper;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
public class AuditLogInputDto {
    private Long id;
    private String serviceName = "PortalApi";
    private String clientName;
    private Long execDuration;
    private String clientIpAddress;
    private String path;
    private String httpStatus;
    private String exception;
    private String browserInfo;
    private String input;
    private String output;

    public AuditLogInputDto(String clientIpAddress, String clientName, String browserInfo, String path, String httpStatus, Exception exception, String input, String output) {
        this.clientIpAddress = clientIpAddress;
        this.clientName = clientName;
        this.browserInfo = browserInfo;
        this.path = path;
        this.httpStatus = httpStatus;
        this.exception = exception == null ? null : ExceptionUtils.getStackTrace(exception);
        this.input = input;
        this.output = output;
    }

    public AuditLogInputDto(AuditLogInputDto auditLog, Date execDurStart) {
        this.clientIpAddress = auditLog.getClientIpAddress();
        this.clientName = auditLog.getClientName();
        this.browserInfo = auditLog.getBrowserInfo();
        this.path = auditLog.getPath();
        this.httpStatus = auditLog.getHttpStatus();
        this.exception = auditLog.getException();
        this.input = auditLog.getInput();
        this.output = auditLog.getOutput();
        this.execDuration = DateHelper.getDuration(execDurStart, new Date(), TimeUnit.MILLISECONDS);
    }
}
