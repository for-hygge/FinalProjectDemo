package com.project.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRequest {
    @NotNull
    private String paymentId;

    @NotNull
    private BigDecimal refundAmount;

    @NotBlank
    private String reason;
}
