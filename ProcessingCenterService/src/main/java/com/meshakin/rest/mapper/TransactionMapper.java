package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.id.TransactionDtoWithId;
import com.meshakin.rest.dto.no.id.TransactionDtoWithoutId;
import com.meshakin.rest.entity.Card;
import com.meshakin.rest.entity.ResponseCode;
import com.meshakin.rest.entity.Terminal;
import com.meshakin.rest.entity.Transaction;
import com.meshakin.rest.entity.TransactionType;
import com.meshakin.rest.repository.CardRepository;
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
    protected CardRepository cardRepository;
    @Autowired
    protected TransactionTypeRepository transactionTypeRepository;
    @Autowired
    protected TerminalRepository terminalRepository;
    @Autowired
    protected ResponseCodeRepository responseCodeRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "card", source = "cardId", qualifiedByName = "idToCard")
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "terminal", source = "terminalId", qualifiedByName = "idToTerminal")
    @Mapping(target = "responseCode", source = "responseCodeId", qualifiedByName = "idToResponseCode")
    public abstract Transaction toEntity(TransactionDtoWithoutId dto);

    @Mapping(target = "cardId", source = "card.id")
    @Mapping(target = "transactionTypeId", source = "transactionType.id")
    @Mapping(target = "terminalId", source = "terminal.id")
    @Mapping(target = "responseCodeId", source = "responseCode.id")
    public abstract TransactionDtoWithId toDtoWithId(Transaction entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "card", source = "cardId", qualifiedByName = "idToCard")
    @Mapping(target = "transactionType", source = "transactionTypeId", qualifiedByName = "idToTransactionType")
    @Mapping(target = "terminal", source = "terminalId", qualifiedByName = "idToTerminal")
    @Mapping(target = "responseCode", source = "responseCodeId", qualifiedByName = "idToResponseCode")
    public abstract void updateEntity(TransactionDtoWithId dto, @MappingTarget Transaction entity);

    @Named("idToCard")
    protected Card card(Long mccId) {
        if (mccId == null) return null;
        return cardRepository.findById(mccId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
    }

    @Named("idToTransactionType")
    protected TransactionType transactionType(Long transactionTypeId) {
        if (transactionTypeId == null) return null;
        return transactionTypeRepository.findById(transactionTypeId)
                .orElseThrow(() -> new EntityNotFoundException("TransactionType not found"));
    }

    @Named("idToTerminal")
    protected Terminal terminal(Long terminalId) {
        if (terminalId == null) return null;
        return terminalRepository.findById(terminalId)
                .orElseThrow(() -> new EntityNotFoundException("Terminal not found"));
    }
    @Named("idToResponseCode")
    protected ResponseCode responseCode(Long responseCode) {
        if (responseCode == null) return null;
        return responseCodeRepository.findById(responseCode)
                .orElseThrow(() -> new EntityNotFoundException("ResponseCode not found"));
    }
}
