package com.meshakin.rest.dto.no.id;

public record ResponseCodeDtoWithoutId (
        String errorCode,
        String errorDescription,
        String errorLevel
){
}
