package com.meshakin.rest.dto;

public record CardStatusDtoWithId(
        Long id
        , String cardStatusName
        , Long version
){
}
