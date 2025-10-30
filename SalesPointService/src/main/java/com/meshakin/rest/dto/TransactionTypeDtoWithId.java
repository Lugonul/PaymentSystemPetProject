package com.meshakin.rest.dto;


public record TransactionTypeDtoWithId(
        Long id,
        String transactionTypeName,
        String operator,
        Long version
){
}
