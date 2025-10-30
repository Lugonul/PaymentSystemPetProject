package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.IssuingBankDtoWithId;
import com.meshakin.rest.dto.IssuingBankDtoWithoutId;
import com.meshakin.rest.entity.IssuingBank;
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
public interface IssuingBankMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    IssuingBank toEntity(IssuingBankDtoWithoutId dto);

    IssuingBankDtoWithId toDtoWithId(IssuingBank entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(IssuingBankDtoWithId dto, @MappingTarget IssuingBank entity);
}