package com.meshakin.rest.dto;




public record SalesPointDtoWithoutId(
        String posName
        , String posAddress
        , String posInn
        , Long acquiringBankId
){
}
