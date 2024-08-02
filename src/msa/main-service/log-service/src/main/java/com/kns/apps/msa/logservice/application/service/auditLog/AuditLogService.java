package com.kns.apps.msa.logservice.application.service.auditLog;

import com.kns.apps.msa.logservice.application.service.auditLog.dto.AuditLogDto;
import com.kns.apps.msa.logservice.application.service.auditLog.dto.AuditLogInputDto;

public interface AuditLogService {

    AuditLogDto createOrEdit(AuditLogInputDto input);

}
