package com.project.paymentservice.service.Impl;

import com.project.paymentservice.dto.Payment;
import com.project.paymentservice.dto.request.CancelRequest;
import com.project.paymentservice.dto.request.CreateRequest;
import com.project.paymentservice.dto.request.RefundRequest;
import com.project.paymentservice.dto.response.PaymentResponse;
import com.project.paymentservice.dto.response.RefundResponse;
import com.project.paymentservice.event.OrderCreatedEvent;
import com.project.paymentservice.event.PaymentCancelledEvent;
import com.project.paymentservice.event.PaymentCreatedEvent;
import com.project.paymentservice.event.PaymentRefundedEvent;
import com.project.paymentservice.producer.PaymentEventProducer;
import com.project.paymentservice.repository.PaymentRepository;
import com.project.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentResponse getPaymentByPaymentId(String paymentId) {
        Payment response = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found, id = " + paymentId));
        return mapper(response);
    }

    public PaymentResponse createPayment(CreateRequest request) {
        log.info("Start a new payment for orderId = {}", request.getOrderId());
        String paymentId = "PAY-" + System.currentTimeMillis();

        Payment payment = Payment.builder()
                .paymentId(paymentId)
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status("PROCESSING")
                .paymentMethod(request.getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.builder()
                .paymentId(saved.getPaymentId())
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .paymentMethod(saved.getPaymentMethod())
                .build();

        paymentEventProducer.sendPaymentCreated(paymentCreatedEvent);

        return mapper(saved);
    }

    public PaymentResponse createPaymentFromEvent(OrderCreatedEvent event) {
        log.info("Create payment from order-created event, orderId={}", event.getOrderId());

        if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
            throw new RuntimeException("Payment already exists for orderId = " + event.getOrderId());
        }

        String paymentId = "PAY-" + System.currentTimeMillis();
        LocalDateTime now = LocalDateTime.now();

        Payment payment = Payment.builder()
                .paymentId(paymentId)
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .status("PROCESSING")
                .paymentMethod(event.getPaymentMethod())
                .createdAt(now)
                .build();

        Payment saved = paymentRepository.save(payment);
        return mapper(saved);
    }

    public PaymentResponse cancelPayment(String paymentId, CancelRequest request) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found, id = " + paymentId));
        payment.setStatus("CANCELLED");
        Payment saved = paymentRepository.save(payment);

        PaymentCancelledEvent event = PaymentCancelledEvent.builder()
                .paymentId(saved.getPaymentId())
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .paymentMethod(saved.getPaymentMethod())
                .reason(request.getReason())
                .build();

        paymentEventProducer.sendPaymentCancelled(event);

        return mapper(saved);
    }

    public RefundResponse refundPayment(String paymentId, RefundRequest request) {
        Payment response = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found, id = " + paymentId));
        response.setStatus("REFUNDED");
        Payment saved = paymentRepository.save(response);

        String refundId = "REF-" + System.currentTimeMillis();

        PaymentRefundedEvent event = PaymentRefundedEvent.builder()
                .refundId(refundId)
                .paymentId(saved.getPaymentId())
                .orderId(saved.getOrderId())
                .userId(saved.getUserId())
                .refundAmount(request.getRefundAmount())
                .reason(request.getReason())
                .build();
        paymentEventProducer.sendPaymentRefunded(event);

        return RefundResponse.builder()
                .refundId(refundId)
                .paymentId(saved.getPaymentId())
                .refundAmount(request.getRefundAmount())
                .status("SUCCESS")
                .reason(request.getReason())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private PaymentResponse mapper(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
