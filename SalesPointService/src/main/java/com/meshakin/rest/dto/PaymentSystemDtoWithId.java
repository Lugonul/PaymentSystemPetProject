package com.meshakin.rest.dto;


public record PaymentSystemDtoWithId(
        Long id
        , String paymentSystemName
        , Long version
){
}
