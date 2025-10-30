package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.TransactionDtoWithId;
import com.meshakin.rest.dto.without.id.TransactionDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.entity.Transaction;
import com.meshakin.rest.entity.TransactionType;
import com.meshakin.rest.repository.AccountRepository;
import com.meshakin.rest.repository.TransactionTypeRepository;
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
public abstract class TransactionMapper {

    @Autowired
    protected TransactionTypeRepository transactionTypeRepository;
    @Autowired
    protected AccountRepository accountRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    public abstract Transaction toEntity(TransactionDtoWithoutId dto);

    @Mapping(target = "transactionTypeId", source = "transactionType.id")
    @Mapping(target = "accountId", source ="account.id")
    public abstract TransactionDtoWithId toDtoWithId(Transaction entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    public abstract void updateEntity(TransactionDtoWithId dto, @MappingTarget Transaction entity);

    @Named("idToTransactionType")
    protected TransactionType idToTransactionType(Long transactionTypeId) {
        if (transactionTypeId == null) return null;
        return transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new EntityNotFoundException("TransactionType not found"));
    }
    @Named("idToAccount")
    protected Account idToAccount(Long accountId) {
        if (accountId == null) return null;
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
}