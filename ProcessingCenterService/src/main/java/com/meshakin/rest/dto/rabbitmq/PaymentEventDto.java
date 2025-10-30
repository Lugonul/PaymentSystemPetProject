package com.meshakin.rest.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEventDto {
    private UUID paymentId;
    private String cardNumber;

    private BigDecimal amount;
    private String currency;
}