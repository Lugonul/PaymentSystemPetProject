package com.meshakin.rest.dto.without.id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionDtoWithoutId (
        LocalDate transactionDate,
        BigDecimal sum,
        String transactionName,
        Long transactionTypeId,
        Long accountId,
        LocalDateTime sentToProcessingCenter,
        LocalDateTime receivedFromProcessingCenterDate
){
}
