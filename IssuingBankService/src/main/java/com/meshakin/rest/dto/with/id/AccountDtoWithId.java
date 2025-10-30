package com.meshakin.rest.dto.with.id;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDtoWithId (
        Long id,
        String accountNumber,
        BigDecimal balance,
        Long currencyId,
        Long accountTypeId,
        Long clientId,
        LocalDate accountOpeningDate,
        Boolean suspendingOperation,
        LocalDate accountClosedDate,
        Long version
){
}
