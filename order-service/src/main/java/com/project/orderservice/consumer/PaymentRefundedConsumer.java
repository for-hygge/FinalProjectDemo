package com.project.orderservice.consumer;

import com.project.orderservice.dto.Order;
import com.project.orderservice.event.PaymentRefundedEvent;
import com.project.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRefundedConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.payment-refunded}",
            groupId = "order-service-group",
            containerFactory = "paymentRefundedKafkaListenerContainerFactory"
    )
    public void consume(PaymentRefundedEvent event) {
        log.info("Received payment-refunded event: {}", event);

        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.getOrderId());

        if (optionalOrder.isEmpty()) {
            log.warn("Order not found, skip event, orderId={}", event.getOrderId());
            return;
        }

        Order order = optionalOrder.get();
        order.setPaymentId(event.getPaymentId());
        order.setStatus("REFUNDED");
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}
