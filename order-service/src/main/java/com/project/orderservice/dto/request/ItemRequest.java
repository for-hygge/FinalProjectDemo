package com.project.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequest {
    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Integer quantity;
}
