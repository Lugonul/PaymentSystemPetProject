package com.meshakin.rest.dto.id;

public record TransactionTypeDtoWithId (
        Long id,
        String transactionTypeName,
        String operator,
        Long version
){
}
