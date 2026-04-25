package com.project.orderservice.controller;

import com.project.orderservice.dto.request.CancelOrderRequest;
import com.project.orderservice.dto.request.CreateOrderRequest;
import com.project.orderservice.dto.response.OrderDetailResponse;
import com.project.orderservice.dto.response.OrderPaymentResponse;
import com.project.orderservice.dto.response.OrderResponse;
import com.project.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "APIs for order management")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get orders by user ID")
    public List<OrderResponse> getOrders(@RequestParam Long userId) {
        return orderService.getOrders(userId);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order detail by order ID")
    public OrderDetailResponse getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/{orderId}/payment")
    @Operation(summary = "Get payment information for an order")
    public OrderPaymentResponse getOrderPayment(@PathVariable String orderId) {
        return orderService.getOrderPayment(orderId);
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel an order")
    public OrderResponse cancelOrder(@PathVariable String orderId, @Valid @RequestBody CancelOrderRequest request) {
        return orderService.cancelOrder(orderId, request);
    }
}
