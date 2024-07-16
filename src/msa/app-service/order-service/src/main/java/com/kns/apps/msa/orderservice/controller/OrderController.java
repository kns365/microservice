package com.kns.apps.msa.orderservice.controller;

import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import com.kns.apps.msa.commonpack.core.model.kafka.Order;
import com.kns.apps.msa.commonpack.core.model.kafka.OrderEvent;
import com.kns.apps.msa.orderservice.service.OrderProducer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @HystrixCommand(fallbackMethod = "fallbackPlaceOrder")
    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    public ResponseEntity<?> fallbackPlaceOrder(Order order) {
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
