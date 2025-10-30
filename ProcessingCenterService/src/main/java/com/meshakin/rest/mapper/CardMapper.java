package com.meshakin.rest.mapper;


import com.meshakin.rest.dto.id.CardDtoWithId;
import com.meshakin.rest.dto.no.id.CardDtoWithoutId;
import com.meshakin.rest.entity.Card;
import com.meshakin.rest.entity.PaymentSystem;
import com.meshakin.rest.repository.PaymentSystemRepository;
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
public abstract class CardMapper {

    @Autowired
    protected PaymentSystemRepository paymentSystemRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "paymentSystem", source = "paymentSystemId", qualifiedByName = "idToPaymentSystem")
    public abstract Card toEntity(CardDtoWithoutId dto);

    @Mapping(target = "paymentSystemId", source = "paymentSystem.id")
    public abstract CardDtoWithId toDtoWithId(Card entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "paymentSystem", source = "paymentSystemId", qualifiedByName = "idToPaymentSystem")
    public abstract void updateEntity(CardDtoWithId dto, @MappingTarget Card entity);

    @Named("idToPaymentSystem")
    protected PaymentSystem idToPaymentSystem(Long paymentSystemId) {
        if (paymentSystemId == null) return null;
        return paymentSystemRepository.findById(paymentSystemId)
                .orElseThrow(() -> new EntityNotFoundException("PaymentSystem not found"));
    }
}