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
public class RefundResponse {
    private String refundId;
    private String paymentId;
    private BigDecimal refundAmount;
    private String status;
    private String reason;
    private LocalDateTime createdAt;
}
