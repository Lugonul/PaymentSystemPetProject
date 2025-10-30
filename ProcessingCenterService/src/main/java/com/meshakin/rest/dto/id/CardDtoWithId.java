package com.meshakin.rest.dto.id;

import java.time.LocalDate;

public record CardDtoWithId(
        Long id,
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long paymentSystemId,
        Long version
) {
}
