package com.meshakin.rest.dto.with.id;

import java.time.LocalDate;

public record ClientDtoWithId (
        Long id,
        String lastName,
        String firstName,
        String middleName,
        LocalDate birthDate,
        String document,
        String address,
        String phone,
        String email,
        Long version
){
}
