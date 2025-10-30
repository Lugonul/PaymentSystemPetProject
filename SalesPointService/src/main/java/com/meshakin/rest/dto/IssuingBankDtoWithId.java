package com.meshakin.rest.dto;

public record IssuingBankDtoWithId(
        Long id
        , String bic
        , String bin
        , String abbreviatedName
        , Long version
) {
}
