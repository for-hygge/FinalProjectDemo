package com.project.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String paymentId;
    private String orderId;
    private Long userId;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private LocalDateTime createdAt;
}
