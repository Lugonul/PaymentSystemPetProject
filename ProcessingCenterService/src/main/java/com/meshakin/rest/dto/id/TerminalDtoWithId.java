package com.meshakin.rest.dto.id;

public record TerminalDtoWithId (
        Long id,
        String terminalId,
        Long mccId,
        Long posId,
        Long version
){
}
