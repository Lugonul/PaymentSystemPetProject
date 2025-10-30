package com.meshakin.rest.dto.no.id;

import java.time.LocalDate;

public record CardDtoWithoutId(
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long paymentSystemId
) {
}
