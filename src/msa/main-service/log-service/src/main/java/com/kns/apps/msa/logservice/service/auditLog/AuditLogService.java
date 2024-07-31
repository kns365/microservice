package com.kns.apps.msa.logservice.service.auditLog;

import com.kns.apps.msa.logservice.service.auditLog.dto.AuditLogDto;
import com.kns.apps.msa.logservice.service.auditLog.dto.AuditLogInputDto;

public interface AuditLogService {

    AuditLogDto createOrEdit(AuditLogInputDto input);

}
