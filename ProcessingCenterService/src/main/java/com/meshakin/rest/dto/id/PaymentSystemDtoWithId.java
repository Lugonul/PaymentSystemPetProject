package com.meshakin.rest.dto.id;

public record PaymentSystemDtoWithId (
        Long id,
        String paymentSystemName,
        Long version
){
}
