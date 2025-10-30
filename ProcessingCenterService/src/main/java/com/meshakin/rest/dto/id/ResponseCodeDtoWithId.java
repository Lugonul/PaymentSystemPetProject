package com.meshakin.rest.dto.id;

public record ResponseCodeDtoWithId (
        Long id,
        String errorCode,
        String errorDescription,
        String errorLevel,
        Long version
){
}
