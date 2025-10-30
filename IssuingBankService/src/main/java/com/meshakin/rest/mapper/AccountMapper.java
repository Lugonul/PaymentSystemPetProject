package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.AccountDtoWithId;
import com.meshakin.rest.dto.without.id.AccountDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.entity.AccountType;
import com.meshakin.rest.entity.Client;
import com.meshakin.rest.entity.Currency;
import com.meshakin.rest.repository.AccountTypeRepository;
import com.meshakin.rest.repository.ClientRepository;
import com.meshakin.rest.repository.CurrencyRepository;
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
    protected AccountTypeRepository accountTypeRepository;
    @Autowired
    protected ClientRepository clientRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "idToCurrency")
    @Mapping(target = "accountType", source = "accountTypeId", qualifiedByName = "idToAccountType")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    public abstract Account toEntity(AccountDtoWithoutId dto);

    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "accountTypeId", source ="accountType.id")
    @Mapping(target = "clientId", source ="client.id")
    public abstract AccountDtoWithId toDtoWithId(Account entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "idToCurrency")
    @Mapping(target = "accountType", source = "accountTypeId", qualifiedByName = "idToAccountType")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    public abstract void updateEntity(AccountDtoWithId dto, @MappingTarget Account entity);

    @Named("idToCurrency")
    protected Currency idToCurrency(Long currencyId) {
        if (currencyId == null) return null;
        return currencyRepository.findById(currencyId)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));
    }
    @Named("idToAccountType")
    protected AccountType idToAccountType(Long accountTypeId) {
        if (accountTypeId == null) return null;
        return accountTypeRepository.findById(accountTypeId)
                .orElseThrow(() -> new EntityNotFoundException("AccountType not found"));
    }
    @Named("idToClient")
    protected Client idToClient(Long clientId) {
        if (clientId == null) return null;
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }
}