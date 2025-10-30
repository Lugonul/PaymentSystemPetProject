package com.meshakin.rest.dto;

public record SalesPointDtoWithId(
        Long id
        , String posName
        , String posAddress
        , String posInn
        , Long acquiringBankId
        , Long version
){
}
