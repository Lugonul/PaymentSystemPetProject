package com.meshakin.rest.dto.with.id;

public record AccountTypeDtoWithId (
        Long id,
        String accountTypeName,
        Long version
){
}
