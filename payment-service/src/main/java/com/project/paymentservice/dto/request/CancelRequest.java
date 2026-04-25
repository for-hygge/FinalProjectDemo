package com.project.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelRequest {
    @NotNull
    private String paymentId;

    @NotBlank
    private String reason;
}
