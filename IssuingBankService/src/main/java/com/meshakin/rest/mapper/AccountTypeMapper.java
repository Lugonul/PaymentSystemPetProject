package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.AccountTypeDtoWithId;
import com.meshakin.rest.dto.without.id.AccountTypeDtoWithoutId;
import com.meshakin.rest.entity.AccountType;
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
public interface AccountTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    AccountType toEntity(AccountTypeDtoWithoutId dto);

    AccountTypeDtoWithId toDtoWithId(AccountType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(AccountTypeDtoWithId dto, @MappingTarget AccountType entity);
}