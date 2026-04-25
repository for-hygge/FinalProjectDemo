package com.project.orderservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotNull
    private Long userId;

    @Valid
    @NotEmpty
    private List<ItemRequest> itemRequests;

    @Valid
    @NotNull
    private ShippingAddressRequest shippingAddress;
}
