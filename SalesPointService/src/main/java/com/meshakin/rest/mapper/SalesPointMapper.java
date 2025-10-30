package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.SalesPointDtoWithId;
import com.meshakin.rest.dto.SalesPointDtoWithoutId;
import com.meshakin.rest.entity.AcquiringBank;
import com.meshakin.rest.entity.SalesPoint;
import com.meshakin.rest.repository.AcquiringBankRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class SalesPointMapper {

    @Autowired
    protected AcquiringBankRepository acquiringBankRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "acquiringBank", source = "acquiringBankId", qualifiedByName = "idToAcquiringBank")
    public abstract SalesPoint toEntity(SalesPointDtoWithoutId dto);

    @Mapping(target = "acquiringBankId", source = "acquiringBank.id")
    public abstract SalesPointDtoWithId toDtoWithId(SalesPoint entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "acquiringBank", source = "acquiringBankId", qualifiedByName = "idToAcquiringBank")
    public abstract void updateEntity(SalesPointDtoWithId dto, @MappingTarget SalesPoint entity);

    @Named("idToAcquiringBank")
    protected AcquiringBank idToAcquiringBank(Long acquiringBankId) {
        if (acquiringBankId == null) return null;
        return acquiringBankRepository.findById(acquiringBankId)
                .orElseThrow(() -> new EntityNotFoundException("AcquiringBank not found"));
    }
}