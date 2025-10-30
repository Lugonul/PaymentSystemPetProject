package com.meshakin.rest.mapper;

import com.meshakin.rest.dto.with.id.CardDtoWithId;
import com.meshakin.rest.dto.without.id.CardDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.entity.Card;
import com.meshakin.rest.entity.CardStatus;
import com.meshakin.rest.entity.Client;
import com.meshakin.rest.entity.PaymentSystem;
import com.meshakin.rest.repository.AccountRepository;
import com.meshakin.rest.repository.CardStatusRepository;
import com.meshakin.rest.repository.ClientRepository;
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
    protected CardStatusRepository cardStatusRepository;
    @Autowired
    protected PaymentSystemRepository paymentSystemRepository;
    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected ClientRepository clientRepository;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "cardStatus", source = "cardStatusId", qualifiedByName = "idToCardStatus")
    @Mapping(target = "paymentSystem", source = "paymentSystemId", qualifiedByName = "idToPaymentSystem")
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    public abstract Card toEntity(CardDtoWithoutId dto);

    @Mapping(target = "cardStatusId", source = "cardStatus.id")
    @Mapping(target = "paymentSystemId", source ="paymentSystem.id")
    @Mapping(target = "accountId", source ="account.id")
    @Mapping(target = "clientId", source ="client.id")
    public abstract CardDtoWithId toDtoWithId(Card entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "cardStatus", source = "cardStatusId", qualifiedByName = "idToCardStatus")
    @Mapping(target = "paymentSystem", source = "paymentSystemId", qualifiedByName = "idToPaymentSystem")
    @Mapping(target = "account", source = "accountId", qualifiedByName = "idToAccount")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "idToClient")
    public abstract void updateEntity(CardDtoWithId dto, @MappingTarget Card entity);

    @Named("idToCardStatus")
    protected CardStatus idToCardStatus(Long cardStatusId) {
        if (cardStatusId == null) return null;
        return cardStatusRepository.findById(cardStatusId)
                .orElseThrow(() -> new EntityNotFoundException("CardStatus not found"));
    }
    @Named("idToPaymentSystem")
    protected PaymentSystem idToPaymentSystem(Long paymentSystemId) {
        if (paymentSystemId == null) return null;
        return paymentSystemRepository.findById(paymentSystemId)
                .orElseThrow(() -> new EntityNotFoundException("PaymentSystem not found"));
    }
    @Named("idToAccount")
    protected Account idToAccount(Long accountId) {
        if (accountId == null) return null;
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
    @Named("idToClient")
    protected Client idToClient(Long clientId) {
        if (clientId == null) return null;
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }
}