package com.meshakin.rest.dto;

public record AcquiringBankDtoWithId(
        Long id
        , String bic
        , String abbreviatedName
        , Long version
) {
}
