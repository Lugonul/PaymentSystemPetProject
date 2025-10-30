package com.meshakin.rest.dto.with.id;

public record CurrencyDtoWithId (
        Long id,
        String currencyDigitalCode,
        String currencyLetterCode,
        String currencyDigitalCodeAccount,
        String currencyName,
        Long version
){
}
