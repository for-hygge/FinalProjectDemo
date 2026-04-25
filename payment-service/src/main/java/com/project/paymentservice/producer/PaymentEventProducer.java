package com.project.paymentservice.producer;

import com.project.paymentservice.event.PaymentCancelledEvent;
import com.project.paymentservice.event.PaymentCreatedEvent;
import com.project.paymentservice.event.PaymentRefundedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.payment-created}")
    private String paymentCreatedTopic;

    @Value("${app.kafka.topic.payment-cancelled}")
    private String paymentCancelledTopic;

    @Value("${app.kafka.topic.payment-refunded}")
    private String paymentRefundedTopic;

    public void sendPaymentCreated(PaymentCreatedEvent event) {
        kafkaTemplate.send(paymentCreatedTopic, event.getOrderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send payment-created event, orderId={}", event.getOrderId(), ex);
                    } else {
                        log.info("Sent payment-created event, orderId={}", event.getOrderId());
                    }
                });
    }

    public void sendPaymentCancelled(PaymentCancelledEvent event) {
        kafkaTemplate.send(paymentCancelledTopic, event.getOrderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send payment-cancelled event, orderId={}", event.getOrderId(), ex);
                    } else {
                        log.info("Sent payment-cancelled event, orderId={}", event.getOrderId());
                    }
                });
    }

    public void sendPaymentRefunded(PaymentRefundedEvent event) {
        kafkaTemplate.send(paymentRefundedTopic, event.getOrderId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send payment-refunded event, orderId={}", event.getOrderId(), ex);
                    } else {
                        log.info("Sent payment-refunded event, orderId={}", event.getOrderId());
                    }
                });
    }
}
