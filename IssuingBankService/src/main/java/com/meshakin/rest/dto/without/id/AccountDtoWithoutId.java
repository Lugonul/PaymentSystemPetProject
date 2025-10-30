package com.meshakin.rest.dto.without.id;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDtoWithoutId (
        String accountNumber,
        BigDecimal balance,
        Long currencyId,
        Long accountTypeId,
        Long clientId,
        LocalDate accountOpeningDate,
        Boolean suspendingOperation,
        LocalDate accountClosedDate
){
}
