package com.meshakin.rest.dto.without.id;

public record CurrencyDtoWithoutId (
        String currencyDigitalCode,
        String currencyLetterCode,
        String currencyDigitalCodeAccount,
        String currencyName
){
}
