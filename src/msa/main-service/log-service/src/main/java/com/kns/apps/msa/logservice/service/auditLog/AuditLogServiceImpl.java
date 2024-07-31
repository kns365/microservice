package com.kns.apps.msa.logservice.service.auditLog;

import com.kns.apps.msa.logservice.core.entity.AuditLog;
import com.kns.apps.msa.logservice.data.AuditLogRepository;
import com.kns.apps.msa.logservice.service.auditLog.dto.AuditLogDto;
import com.kns.apps.msa.logservice.service.auditLog.dto.AuditLogInputDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public AuditLogDto createOrEdit(AuditLogInputDto input) {
        AuditLogDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private AuditLogDto create(AuditLogInputDto input) {
        AuditLog data = modelMapper.map(input, AuditLog.class);
        return modelMapper.map(auditLogRepository.save(data), AuditLogDto.class);
    }

    private AuditLogDto edit(AuditLogInputDto input) {
        AuditLog data = auditLogRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(auditLogRepository.save(data), AuditLogDto.class);
    }
}
