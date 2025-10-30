package com.meshakin.rest.dto.without.id;

import java.time.LocalDate;

public record ClientDtoWithoutId (
        String lastName,
        String firstName,
        String middleName,
        LocalDate birthDate,
        String document,
        String address,
        String phone,
        String email
){
}
