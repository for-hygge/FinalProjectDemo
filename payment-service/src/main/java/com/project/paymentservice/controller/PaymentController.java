package com.project.paymentservice.controller;

import com.project.paymentservice.dto.request.CancelRequest;
import com.project.paymentservice.dto.request.CreateRequest;
import com.project.paymentservice.dto.request.RefundRequest;
import com.project.paymentservice.dto.response.PaymentResponse;
import com.project.paymentservice.dto.response.RefundResponse;
import com.project.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "APIs for payment management")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by payment ID")
    public PaymentResponse getPaymentByPaymentId(@PathVariable String paymentId) {
        return paymentService.getPaymentByPaymentId(paymentId);
    }

    @PostMapping
    @Operation(summary = "Create a new payment")
    public PaymentResponse createPayment(@Valid @RequestBody CreateRequest request) {
        return paymentService.createPayment(request);
    }

    @PostMapping("/{paymentId}/cancel")
    @Operation(summary = "Cancel a payment")
    public PaymentResponse cancelPayment(@PathVariable String paymentId, @Valid @RequestBody CancelRequest request) {
        return paymentService.cancelPayment(paymentId, request);
    }

    @PostMapping("/{paymentId}/refund")
    @Operation(summary = "Refund a payment")
    public RefundResponse refundPayment(@PathVariable String paymentId, @Valid @RequestBody RefundRequest request) {
        return paymentService.refundPayment(paymentId, request);
    }
}
