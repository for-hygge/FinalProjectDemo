package com.project.paymentservice.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String id;

    private String paymentId;

    private String orderId;

    private Long userId;

    private BigDecimal amount;

    private String status;

    private String paymentMethod;

    private LocalDateTime createdAt;
}
