package com.project.orderservice.consumer;

import com.project.orderservice.dto.Order;
import com.project.orderservice.event.PaymentCreatedEvent;
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
public class PaymentCreatedConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.payment-created}",
            groupId = "order-service-group",
            containerFactory = "paymentCreatedKafkaListenerContainerFactory"
    )
    public void consume(PaymentCreatedEvent event) {
        log.info("Received payment-created event: {}", event);

        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.getOrderId());

        if (optionalOrder.isEmpty()) {
            log.warn("Skip payment-created event because order not found, orderId={}", event.getOrderId());
            return;
        }

        Order order = optionalOrder.get();
        order.setPaymentId(event.getPaymentId());
        order.setStatus("PAYMENT_CREATED");
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        log.info("Order updated successfully, orderId={}, paymentId={}", order.getOrderId(), order.getPaymentId());
    }
}
