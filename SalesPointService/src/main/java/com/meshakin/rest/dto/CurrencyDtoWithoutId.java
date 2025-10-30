package com.meshakin.rest.dto;

public record CurrencyDtoWithoutId(String currencyName
        , String currencyDigitalCode
        , String currencyLetterCode) {
}
