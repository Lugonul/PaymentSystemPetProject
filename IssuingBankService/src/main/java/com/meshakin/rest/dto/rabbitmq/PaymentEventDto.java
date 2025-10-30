package com.meshakin.rest.dto.rabbitmq;


import java.math.BigDecimal;
import java.util.UUID;

public record PaymentEventDto(
        UUID paymentId,
        String cardNumber,
        BigDecimal amount,
        String currency
) {
}