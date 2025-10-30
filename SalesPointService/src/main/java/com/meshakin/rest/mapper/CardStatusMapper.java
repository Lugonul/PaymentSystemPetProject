package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.CardStatusDtoWithId;
import com.meshakin.rest.dto.CardStatusDtoWithoutId;
import com.meshakin.rest.entity.CardStatus;
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
public interface CardStatusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    CardStatus toEntity(CardStatusDtoWithoutId dto);

    CardStatusDtoWithId toDtoWithId(CardStatus entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(CardStatusDtoWithId dto, @MappingTarget CardStatus entity);
}