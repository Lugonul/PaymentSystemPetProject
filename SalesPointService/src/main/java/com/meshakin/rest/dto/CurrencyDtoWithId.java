package com.meshakin.rest.dto;

public record CurrencyDtoWithId(
        Long id
        , String currencyDigitalCode
        , String currencyLetterCode
        , String currencyName
        , Long version
) {
}
