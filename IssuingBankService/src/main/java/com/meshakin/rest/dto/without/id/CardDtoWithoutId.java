package com.meshakin.rest.dto.without.id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardDtoWithoutId (
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long cardStatusId,
        Long paymentSystemId,
        Long accountId,
        Long clientId,
        LocalDateTime sentToProcessingCenter,
        LocalDateTime receivedFromProcessingCenterDate
) {
}
