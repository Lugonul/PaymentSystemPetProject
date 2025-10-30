package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.ClientDtoWithId;
import com.meshakin.rest.dto.without.id.ClientDtoWithoutId;
import com.meshakin.rest.entity.Client;
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
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Client toEntity(ClientDtoWithoutId dto);

    ClientDtoWithId toDtoWithId(Client entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(ClientDtoWithId dto, @MappingTarget Client entity);
}