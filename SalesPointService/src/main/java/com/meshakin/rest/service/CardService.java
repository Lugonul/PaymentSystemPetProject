package com.meshakin.rest.service;

import com.meshakin.rest.dto.CardDtoWithId;
import com.meshakin.rest.dto.CardDtoWithoutId;
import com.meshakin.rest.entity.Card;
import com.meshakin.rest.mapper.CardMapper;
import com.meshakin.rest.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardMapper cardMapper;
    private final CardRepository cardRepository;

    @Transactional
    public CardDtoWithId create(CardDtoWithoutId cardDtoWithoutId) {
        Card Card = cardMapper.toEntity(cardDtoWithoutId);
        Card savedCard = cardRepository.save(Card);

        return cardMapper.toDtoWithId(savedCard);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:Cards", key = "#id")
    public Optional<CardDtoWithId> read(Long id) {

        Optional<CardDtoWithId> maybeCard = cardRepository.findById(id)
                .map(cardMapper::toDtoWithId);

        return maybeCard;
    }

    @Transactional(readOnly = true)
    public List<CardDtoWithId> readAll() {
        return cardRepository.findAll().stream()
                .map(cardMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:Cards", key = "#dto.id")
    public CardDtoWithId update(CardDtoWithId cardDtoWithId) {
        Card Card = cardRepository.findById(cardDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Card.getVersion().equals(cardDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        cardMapper.updateEntity(cardDtoWithId, Card);
        cardRepository.flush();

        return cardMapper.toDtoWithId(Card);
    }

    @Transactional
    @CacheEvict(value = "service1:Cards", key = "#id")
    public void delete(Long id) {
        Card Card = cardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        cardRepository.delete(Card);
    }

    @Transactional
    @CacheEvict(value = "service1:Cards", allEntries = true)
    public void deleteAll() {
        cardRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        cardRepository.dropTable();
    }
}