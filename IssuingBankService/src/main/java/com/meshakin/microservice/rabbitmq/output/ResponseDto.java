package com.meshakin.microservice.rabbitmq.output;

public record ResponseDto(
        Long transactionId,
        Long responseCodeId
        ) {
}
