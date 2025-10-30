package com.meshakin.rest.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionDtoWithoutId(
        LocalDate transactionDate,
        BigDecimal sum,
        String transactionName,
        Long accountId,
        Long transactionTypeId,
        Long cardId,
        Long terminalId,
        Long responseCodeId,
        String authorizationCode,
        LocalDateTime receivedFromIssuingBank,
        LocalDateTime sentToIssuingBank
){
}
