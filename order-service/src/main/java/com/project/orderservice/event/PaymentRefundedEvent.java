package com.project.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRefundedEvent {
    private String refundId;
    private String paymentId;
    private String orderId;
    private Long userId;
    private BigDecimal refundAmount;
    private String status;
    private String reason;
}
