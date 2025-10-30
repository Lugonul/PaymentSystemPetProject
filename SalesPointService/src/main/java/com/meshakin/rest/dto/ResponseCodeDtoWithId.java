package com.meshakin.rest.dto;


public record ResponseCodeDtoWithId(
        Long id
        , String errorCode
        , String errorDescription
        , String errorLevel
        , Long version
) {
}
