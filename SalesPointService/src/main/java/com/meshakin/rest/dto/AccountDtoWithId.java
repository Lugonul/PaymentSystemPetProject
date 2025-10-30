package com.meshakin.rest.dto;

import java.math.BigDecimal;

public record AccountDtoWithId(
        Long id,
        String accountNumber,
        BigDecimal balance,
        Long currencyId,
        Long issuingBankId,
        Long version
) {
}
