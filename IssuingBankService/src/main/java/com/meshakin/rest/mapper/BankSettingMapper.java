package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.BankSettingDtoWithId;
import com.meshakin.rest.dto.without.id.BankSettingDtoWithoutId;
import com.meshakin.rest.entity.BankSetting;
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
public interface BankSettingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    BankSetting toEntity(BankSettingDtoWithoutId dto);

    BankSettingDtoWithId toDtoWithId(BankSetting entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(BankSettingDtoWithId dto, @MappingTarget BankSetting entity);
}