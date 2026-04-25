package com.project.orderservice.service;

import com.project.orderservice.dto.request.CancelOrderRequest;
import com.project.orderservice.dto.request.CreateOrderRequest;
import com.project.orderservice.dto.response.OrderDetailResponse;
import com.project.orderservice.dto.response.OrderPaymentResponse;
import com.project.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders(Long userId);

    OrderDetailResponse getOrderById(String orderId);

    OrderPaymentResponse getOrderPayment(String orderId);

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse cancelOrder(String orderId, CancelOrderRequest request);
}
