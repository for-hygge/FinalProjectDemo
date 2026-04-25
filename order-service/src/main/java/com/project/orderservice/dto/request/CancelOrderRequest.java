package com.project.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelOrderRequest {
    @NotNull
    private String orderId;

    @NotBlank
    private String reason;
}
