package com.project.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateRequest {
    @NotBlank
    private String orderId;

    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String paymentMethod;
}
