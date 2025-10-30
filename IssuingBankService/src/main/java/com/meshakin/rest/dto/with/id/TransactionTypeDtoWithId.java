package com.meshakin.rest.dto.with.id;

public record TransactionTypeDtoWithId (
        Long id,
        String transactionTypeName,
        Long version
){
}
