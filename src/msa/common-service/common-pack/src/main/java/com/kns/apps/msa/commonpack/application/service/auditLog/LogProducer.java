package com.kns.apps.msa.commonpack.application.service.auditLog;

import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public boolean isConnected() {
        try {
            // Lấy đối tượng KafkaProducer từ KafkaTemplate
            Producer<String, LogEvent> kafkaProducer =  kafkaTemplate.getProducerFactory().createProducer();

            // Lấy metrics của KafkaProducer
            Map<MetricName, ? extends Metric> metrics = kafkaProducer.metrics();

            // Kiểm tra xem metrics có trống hay không
            return metrics != null && !metrics.isEmpty();
        } catch (Exception e) {
            // Nếu có lỗi xảy ra, kết nối không thành công
            return false;
        }
    }
}
