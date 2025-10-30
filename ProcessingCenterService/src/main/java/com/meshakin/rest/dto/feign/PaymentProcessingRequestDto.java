package com.meshakin.rest.dto.feign;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentProcessingRequestDto {
    private UUID paymentId = UUID.randomUUID();
    private String cardNumber;
    private BigDecimal amount;
    private String currency;
    private String terminalId; // Добавьте это поле
}