package vn.com.kns.portalapi.application.service.administration.auditLog;

import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogDto;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface AuditLogService {

    List<AuditLogDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    AuditLogDto getById(Long id);

    void deleteById(Long id);

    AuditLogDto createOrEdit(AuditLogInputDto input);

}
