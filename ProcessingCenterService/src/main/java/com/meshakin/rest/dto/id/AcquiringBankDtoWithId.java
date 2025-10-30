package com.meshakin.rest.dto.id;

public record AcquiringBankDtoWithId(
        Long id,
        String bic,
        String abbreviatedName,
        Long version
) {
}

