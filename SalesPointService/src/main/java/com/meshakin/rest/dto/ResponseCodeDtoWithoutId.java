package com.meshakin.rest.dto;

public record ResponseCodeDtoWithoutId(
        String errorCode
        , String errorDescription
        , String errorLevel
) {
}
