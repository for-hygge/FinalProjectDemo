package com.project.paymentservice.service;

import com.project.paymentservice.dto.request.CancelRequest;
import com.project.paymentservice.dto.request.CreateRequest;
import com.project.paymentservice.dto.request.RefundRequest;
import com.project.paymentservice.dto.response.PaymentResponse;
import com.project.paymentservice.dto.response.RefundResponse;
import com.project.paymentservice.event.OrderCreatedEvent;

public interface PaymentService {
    PaymentResponse getPaymentByPaymentId(String paymentId);

    PaymentResponse createPayment(CreateRequest request);

    PaymentResponse createPaymentFromEvent(OrderCreatedEvent event);

    PaymentResponse cancelPayment(String paymentId, CancelRequest request);

    RefundResponse refundPayment(String paymentId, RefundRequest request);
}
