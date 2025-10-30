package com.meshakin.rest.dto.with.id;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardDtoWithId (
        Long id,
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long cardStatusId,
        Long paymentSystemId,
        Long accountId,
        Long clientId,
        LocalDateTime sentToProcessingCenter,
        LocalDateTime receivedFromProcessingCenterDate,
        Long version
){
}
