package com.meshakin.rest.dto.with.id;

public record PaymentSystemDtoWithId(
        Long id,
        String paymentSystemName,
        String firstDigitBin,
        Long version
) {
}
