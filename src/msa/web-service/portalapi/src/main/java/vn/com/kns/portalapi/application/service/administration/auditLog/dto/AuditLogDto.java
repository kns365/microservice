package vn.com.kns.portalapi.application.service.administration.auditLog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {
    private Long id;
    private Date createdDate;
    private String createdBy;
    private Long execDuration;
    private String clientIpAddress;
    private String clientName;
    private String browserInfo;
    private String path;
    private String httpStatus;
    private String exception;
    private String input;
    private String output;
}
