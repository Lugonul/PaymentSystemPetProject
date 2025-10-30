package com.meshakin.rest.dto.no.id;

public record TerminalDtoWithoutId (
        String terminalId,
        Long mccId,
        Long posId
){
}
