package com.meshakin.rest.dto.with.id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionDtoWithId (
        Long id,
        LocalDate transactionDate,
        BigDecimal sum,
        String transactionName,
        Long transactionTypeId,
        Long accountId,
        LocalDateTime sentToProcessingCenter,
        LocalDateTime receivedFromProcessingCenterDate,
        Long version
) {
}
