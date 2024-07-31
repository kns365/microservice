package com.kns.apps.msa.logservice.service;

import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import com.kns.apps.msa.logservice.service.auditLog.AuditLogService;
import com.kns.apps.msa.logservice.service.auditLog.dto.AuditLogInputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogConsumer {

    private static AuditLogService auditLogService;

    public LogConsumer(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(LogEvent event) {
        try {
            log.info(String.format("Log event received in log-service => %s", event.toString()));
            auditLogService.createOrEdit(new AuditLogInputDto(event));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error createOrEdit LogEvent");
        }
    }
}
