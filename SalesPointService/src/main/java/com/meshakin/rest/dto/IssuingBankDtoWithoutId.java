package com.meshakin.rest.dto;

public record IssuingBankDtoWithoutId(
        String bic
        , String bin
        , String abbreviatedName
){
}
