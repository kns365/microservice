package com.kns.apps.microservice.stockservice.service;

import com.kns.apps.microservice.configserver.core.model.kafka.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event) {
        log.info(String.format("Order event received in stock service => %s", event.toString()));
        // save the order event into the database
    }
}
