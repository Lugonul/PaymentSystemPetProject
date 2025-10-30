package com.meshakin.rest.dto.with.id;

public record BankSettingDtoWithId(
        Long id,
        String setting,
        String currentValue,
        String description,
        Long version
){
}
