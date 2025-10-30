package com.meshakin.rest.dto.id;

public record SalesPointDtoWithId (
        Long id,
        String posName,
        String posAddress,
        String posInn,
        Long acquiringBankId,
        Long version
){
}
