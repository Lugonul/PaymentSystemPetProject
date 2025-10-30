package com.meshakin.rest.dto.no.id;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDtoWithoutId (
        LocalDate transactionDate,
        BigDecimal sum,
        Long transactionTypeId,
        Long cardId,
        Long terminalId,
        Long responseCodeId,
        String authorizationCode
){
}
