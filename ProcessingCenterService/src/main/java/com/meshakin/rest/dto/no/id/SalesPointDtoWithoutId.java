package com.meshakin.rest.dto.no.id;

public record SalesPointDtoWithoutId (
        String posName,
        String posAddress,
        String posInn,
        Long acquiringBankId
){
}