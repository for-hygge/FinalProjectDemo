package com.project.orderservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingAddressRequest {
    @NotBlank
    private String customerName;

    @NotBlank
    private String phone;

    @Email
    private String email;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;
}
