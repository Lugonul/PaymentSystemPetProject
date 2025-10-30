package com.meshakin.microservice.rabbitmq.input;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDto (
        Long id,
        LocalDate transactionDate,
        BigDecimal sum,
        Long transactionTypeId,
        Long cardId,
        Long terminalId,
        Long responseCodeId,
        String authorizationCode,
        Long version
) {
}
