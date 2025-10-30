package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.PaymentSystemDtoWithId;
import com.meshakin.rest.dto.PaymentSystemDtoWithoutId;
import com.meshakin.rest.entity.PaymentSystem;
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
public interface PaymentSystemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    PaymentSystem toEntity(PaymentSystemDtoWithoutId dto);

    PaymentSystemDtoWithId toDtoWithId(PaymentSystem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(PaymentSystemDtoWithId dto, @MappingTarget PaymentSystem entity);
}