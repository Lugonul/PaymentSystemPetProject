package com.meshakin.microservices.rabbitmq.input;

public record ResponseDto(
        Long transactionId,
        Long responseCodeId
) {
}
