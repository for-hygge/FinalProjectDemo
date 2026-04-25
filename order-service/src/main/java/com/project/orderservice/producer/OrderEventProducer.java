package com.project.orderservice.producer;

import com.project.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.order-created}")
    private String orderCreatedTopic;

    public void sendOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(orderCreatedTopic, event.getOrderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send order-created event, orderId={}", event.getOrderId(), ex);
                    } else {
                        log.info("Sent order-created event, orderId={}", event.getOrderId());
                    }
                });
    }
}
