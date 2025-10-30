package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.id.AcquiringBankDtoWithId;
import com.meshakin.rest.dto.no.id.AcquiringBankDtoWithoutId;
import com.meshakin.rest.entity.AcquiringBank;
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
public interface AcquiringBankMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    AcquiringBank toEntity(AcquiringBankDtoWithoutId dto);

    AcquiringBankDtoWithId toDtoWithId(AcquiringBank entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(AcquiringBankDtoWithId dto, @MappingTarget AcquiringBank entity);
}