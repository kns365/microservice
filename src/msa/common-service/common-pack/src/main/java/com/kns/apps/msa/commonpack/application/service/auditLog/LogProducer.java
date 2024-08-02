package com.kns.apps.msa.commonpack.application.service.auditLog;

import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogProducer {
    @Value("${server.kafka.topic.log}") // create log_topics
    private String topicName;

    @Autowired
    private KafkaTemplate<String, LogEvent> kafkaTemplate;

    public void sendMessage(LogEvent event) {
        Message<LogEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
    }
}
