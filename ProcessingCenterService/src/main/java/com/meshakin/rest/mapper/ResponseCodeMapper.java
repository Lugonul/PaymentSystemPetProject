package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.id.ResponseCodeDtoWithId;
import com.meshakin.rest.dto.no.id.ResponseCodeDtoWithoutId;
import com.meshakin.rest.entity.ResponseCode;
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
public interface ResponseCodeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    ResponseCode toEntity(ResponseCodeDtoWithoutId dto);

    ResponseCodeDtoWithId toDtoWithId(ResponseCode entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(ResponseCodeDtoWithId dto, @MappingTarget ResponseCode entity);
}