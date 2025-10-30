package com.meshakin.rest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardDtoWithoutId(
        String cardNumber,
        LocalDate expirationDate,
        String holderName,
        Long cardStatusId,
        Long paymentSystemId,
        Long accountId,
        LocalDateTime receivedFromIssuingBank,
        LocalDateTime sentToIssuingBank
){
}
