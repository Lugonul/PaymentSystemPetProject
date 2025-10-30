package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.TransactionDtoWithId;
import com.meshakin.rest.dto.TransactionDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.entity.ResponseCode;
import com.meshakin.rest.entity.Terminal;
import com.meshakin.rest.entity.Transaction;
import com.meshakin.rest.entity.TransactionType;
import com.meshakin.rest.repository.AccountRepository;
import com.meshakin.rest.repository.ResponseCodeRepository;
import com.meshakin.rest.repository.TerminalRepository;
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
    protected AccountRepository accountRepository;
    @Autowired
    protected TransactionTypeRepository transactionTypeRepository;
    @Autowired
    protected TerminalRepository terminalRepository;
    @Autowired
    protected ResponseCodeRepository  responseCodeRepository;



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "terminal", source = "terminalId", qualifiedByName = "idToTerminal")
    @Mapping(target = "responseCode", source = "responseCodeId", qualifiedByName = "idToResponseCode")
    public abstract Transaction toEntity(TransactionDtoWithoutId dto);

    @Mapping(target = "accountId", source ="account.id")
    @Mapping(target = "transactionTypeId", source = "transactionType.id")
    @Mapping(target = "terminalId", source ="terminal.id")
    @Mapping(target = "responseCodeId", source ="responseCode.id")
    public abstract TransactionDtoWithId toDtoWithId(Transaction entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "terminal", source = "terminalId", qualifiedByName = "idToTerminal")
    @Mapping(target = "responseCode", source = "responseCodeId", qualifiedByName = "idToResponseCode")
    public abstract void updateEntity(TransactionDtoWithId dto, @MappingTarget Transaction entity);

    @Named("idToAccount")
    protected Account idToAccount(Long accountId) {
        if (accountId == null) return null;
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
    @Named("idToTransactionType")
    protected TransactionType idToTransactionType(Long transactionTypeId) {
        if (transactionTypeId == null) return null;
        return transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new EntityNotFoundException("TransactionType not found"));
    }
    @Named("idToTerminal")
    protected Terminal idToTerminal(Long terminalId) {
        if (terminalId == null) return null;
        return terminalRepository.findById(terminalId)
                .orElseThrow(() -> new EntityNotFoundException("Terminal not found"));
    }
    @Named("idToResponseCode")
    protected ResponseCode idToResponseCode(Long responseCode) {
        if  (responseCode == null) return null;
        return responseCodeRepository.findById(responseCode)
                .orElseThrow(() -> new EntityNotFoundException("ResponseCode not found"));
    }
}