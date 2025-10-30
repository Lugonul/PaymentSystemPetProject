package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.id.MerchantCategoryCodeDtoWithId;
import com.meshakin.rest.dto.no.id.MerchantCategoryCodeDtoWithoutId;
import com.meshakin.rest.entity.MerchantCategoryCode;
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
public interface MerchantCategoryCodeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    MerchantCategoryCode toEntity(MerchantCategoryCodeDtoWithoutId dto);

    MerchantCategoryCodeDtoWithId toDtoWithId(MerchantCategoryCode entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(MerchantCategoryCodeDtoWithId dto, @MappingTarget MerchantCategoryCode entity);
}
