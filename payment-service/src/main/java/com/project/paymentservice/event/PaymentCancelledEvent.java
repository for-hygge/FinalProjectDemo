package com.project.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private Long userId;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private String reason;
}
