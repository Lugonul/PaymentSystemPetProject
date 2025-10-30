package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.TerminalDtoWithId;
import com.meshakin.rest.dto.TerminalDtoWithoutId;
import com.meshakin.rest.entity.MerchantCategoryCode;
import com.meshakin.rest.entity.SalesPoint;
import com.meshakin.rest.entity.Terminal;
import com.meshakin.rest.repository.MerchantCategoryCodeRepository;
import com.meshakin.rest.repository.SalesPointRepository;
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
public abstract class TerminalMapper {

    @Autowired
    protected MerchantCategoryCodeRepository merchantCategoryCodeRepository;
    @Autowired
    protected SalesPointRepository salesPointRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "mcc",source = "mccId", qualifiedByName = "idToMcc")
    @Mapping(target = "pos", source = "posId", qualifiedByName = "idToPos")
    public abstract Terminal toEntity(TerminalDtoWithoutId dto);

    @Mapping(target = "mccId", source = "mcc.id")
    @Mapping(target = "posId", source = "pos.id")
    public abstract TerminalDtoWithId toDtoWithId(Terminal entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "mcc",source = "mccId", qualifiedByName = "idToMcc")
    @Mapping(target = "pos", source = "posId", qualifiedByName = "idToPos")
    public abstract void updateEntity(TerminalDtoWithId dto, @MappingTarget Terminal entity);

    @Named("idToMcc")
    protected MerchantCategoryCode merchantCategoryCode(Long mccId) {
        if (mccId == null) return null;
        return merchantCategoryCodeRepository.findById(mccId)
                .orElseThrow(() -> new EntityNotFoundException("MerchantCategoryCode not found"));
    }
    @Named("idToPos")
    protected SalesPoint salesPoint(Long salesPointId) {
        if (salesPointId == null) return null;
        return salesPointRepository.findById(salesPointId)
                .orElseThrow(() -> new EntityNotFoundException("SalesPoint not found"));
    }
}