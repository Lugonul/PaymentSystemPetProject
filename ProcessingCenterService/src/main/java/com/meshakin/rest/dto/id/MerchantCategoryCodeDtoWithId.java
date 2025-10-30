package com.meshakin.rest.dto.id;

public record MerchantCategoryCodeDtoWithId (
        Long id,
        String mcc,
        String mccName,
        Long version
){
}
