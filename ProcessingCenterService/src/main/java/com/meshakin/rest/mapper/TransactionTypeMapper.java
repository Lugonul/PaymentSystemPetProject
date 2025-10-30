package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.id.TransactionTypeDtoWithId;
import com.meshakin.rest.dto.no.id.TransactionTypeDtoWithoutId;
import com.meshakin.rest.entity.TransactionType;
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
public interface TransactionTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    TransactionType toEntity(TransactionTypeDtoWithoutId dto);

    TransactionTypeDtoWithId toDtoWithId(TransactionType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(TransactionTypeDtoWithId dto, @MappingTarget TransactionType entity);
}