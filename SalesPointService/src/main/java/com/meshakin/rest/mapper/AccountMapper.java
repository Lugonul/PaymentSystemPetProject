package com.meshakin.rest.mapper;


import com.meshakin.rest.dto.AccountDtoWithId;
import com.meshakin.rest.dto.AccountDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.entity.Currency;
import com.meshakin.rest.entity.IssuingBank;
import com.meshakin.rest.repository.CurrencyRepository;
import com.meshakin.rest.repository.IssuingBankRepository;
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
public abstract class AccountMapper {

    @Autowired
    protected CurrencyRepository currencyRepository;
    @Autowired
    protected IssuingBankRepository issuingBankRepository;



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "idToCurrency")
    @Mapping(target = "issuingBank", source = "issuingBankId", qualifiedByName = "idToIssuingBank")
    public abstract Account toEntity(AccountDtoWithoutId dto);

    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "issuingBankId", source ="issuingBank.id")
    public abstract AccountDtoWithId toDtoWithId(Account entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "idToCurrency")
    @Mapping(target = "issuingBank", source = "issuingBankId", qualifiedByName = "idToIssuingBank")
    public abstract void updateEntity(AccountDtoWithId dto, @MappingTarget Account entity);

    @Named("idToCurrency")
    protected Currency idToCurrency(Long currencyId) {
        if (currencyId == null) return null;
        return currencyRepository.findById(currencyId)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));
    }
    @Named("idToIssuingBank")
    protected IssuingBank idToIssuingBank(Long issuingBankId) {
        if (issuingBankId == null) return null;
        return issuingBankRepository.findById(issuingBankId)
                .orElseThrow(() -> new EntityNotFoundException("IssuingBank not found"));
    }
}