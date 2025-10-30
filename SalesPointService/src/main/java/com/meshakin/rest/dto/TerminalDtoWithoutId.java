package com.meshakin.rest.dto;

public record TerminalDtoWithoutId(
        String terminalId
        , Long mccId
        , Long posId
){
}
