package com.project.orderservice.consumer;

import com.project.orderservice.dto.Order;
import com.project.orderservice.event.PaymentCancelledEvent;
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
public class PaymentCancelledConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.payment-cancelled}",
            groupId = "order-service-group",
            containerFactory = "paymentCancelledKafkaListenerContainerFactory"
    )
    public void consume(PaymentCancelledEvent event) {
        log.info("Received payment-cancelled event: {}", event);

        Optional<Order> optionalOrder = orderRepository.findByOrderId(event.getOrderId());

        if (optionalOrder.isEmpty()) {
            log.warn("Order not found, skip event, orderId={}", event.getOrderId());
            return;
        }

        Order order = optionalOrder.get();
        order.setPaymentId(event.getPaymentId());
        order.setStatus("PAYMENT_CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}
