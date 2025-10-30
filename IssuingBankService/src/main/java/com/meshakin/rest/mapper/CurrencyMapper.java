package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.CurrencyDtoWithId;
import com.meshakin.rest.dto.without.id.CurrencyDtoWithoutId;
import com.meshakin.rest.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Currency toEntity(CurrencyDtoWithoutId dto);

    CurrencyDtoWithId toDtoWithId(Currency entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(CurrencyDtoWithId dto, @MappingTarget Currency entity);
}