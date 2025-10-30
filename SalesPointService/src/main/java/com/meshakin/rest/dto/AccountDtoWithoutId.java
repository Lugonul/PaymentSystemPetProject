package com.meshakin.rest.dto;

import java.math.BigDecimal;

public record AccountDtoWithoutId(
        String accountNumber,
        BigDecimal balance,
        Long currencyId,
        Long issuingBankId
){
}
