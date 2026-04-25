package com.project.orderservice.service.impl;

import com.project.orderservice.dto.Order;
import com.project.orderservice.dto.request.CancelOrderRequest;
import com.project.orderservice.dto.request.CreateOrderRequest;
import com.project.orderservice.dto.response.OrderDetailResponse;
import com.project.orderservice.dto.response.OrderPaymentResponse;
import com.project.orderservice.dto.response.OrderResponse;
import com.project.orderservice.event.OrderCreatedEvent;
import com.project.orderservice.producer.OrderEventProducer;
import com.project.orderservice.repository.OrderRepository;
import com.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public List<OrderResponse> getOrders(Long userId) {
        log.info("Getting orders by userId = {}", userId);

        return orderRepository.findByUserId(userId).stream()
                .map(this::orderResponseMapper)
                .toList();
    }

    public OrderDetailResponse getOrderById(String orderId) {
        log.info("Getting order details by orderId = {}", orderId);

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found, orderId = " + orderId));

        return OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(List.of())
                .shippingAddress(null)
                .build();
    }

    public OrderPaymentResponse getOrderPayment(String orderId) {
        log.info("Getting payment details by orderId = {}", orderId);

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found, orderId = " + orderId));

        return OrderPaymentResponse.builder()
                .orderId(order.getOrderId())
                .paymentId(order.getPaymentId())
                .paymentStatus(order.getPaymentId() == null ? "NOT_CREATED" : order.getStatus())
                .paymentMethod("CREDIT_CARD")
                .amount(order.getTotalAmount())
                .build();
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        log.info("Creating an order for userId = {}", request.getUserId());

        BigDecimal totalAmount = request.getItemRequests().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String orderId = "ORD-" + System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.now();

        Order order = Order.builder()
                .orderId(orderId)
                .userId(request.getUserId())
                .status("PENDING_PAYMENT")
                .totalAmount(totalAmount)
                .paymentId(null)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Order saved = orderRepository.save(order);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .amount(saved.getTotalAmount())
                .paymentMethod("CREDIT_CARD")
                .build();
        orderEventProducer.sendOrderCreated(event);

        return orderResponseMapper(saved);
    }

    public OrderResponse cancelOrder(String orderId, CancelOrderRequest request) {
        log.info("Cancelling an order for orderId = {}", orderId);

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found, orderId = " + orderId));

        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());

        Order updated = orderRepository.save(order);
        return orderResponseMapper(updated);
    }

    private OrderResponse orderResponseMapper(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .paymentId(order.getPaymentId())
                .build();
    }
}
