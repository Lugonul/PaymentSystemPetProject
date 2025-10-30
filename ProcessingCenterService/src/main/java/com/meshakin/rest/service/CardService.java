package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.CardDtoWithId;
import com.meshakin.rest.dto.no.id.CardDtoWithoutId;
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
    private final CardRepository CardRepository;

    @Transactional
    public CardDtoWithId create(CardDtoWithoutId CardDtoWithoutId) {
        Card Card = cardMapper.toEntity(CardDtoWithoutId);
        Card savedCard = CardRepository.save(Card);

        return cardMapper.toDtoWithId(savedCard);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:cards", key = "#id")
    public Optional<CardDtoWithId> read(Long id) {

        Optional<CardDtoWithId> maybeCard = CardRepository.findById(id)
                .map(cardMapper::toDtoWithId);

        return maybeCard;
    }

    @Transactional(readOnly = true)
    public List<CardDtoWithId> readAll() {
        return CardRepository.findAll().stream()
                .map(cardMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:cards", key = "#dto.id")
    public CardDtoWithId update(CardDtoWithId CardDtoWithId) {
        Card Card = CardRepository.findById(CardDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Card.getVersion().equals(CardDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        cardMapper.updateEntity(CardDtoWithId, Card);
        CardRepository.flush();

        return cardMapper.toDtoWithId(Card);
    }

    @Transactional
    @CacheEvict(value = "service2:cards", key = "#id")
    public void delete(Long id) {
        Card Card = CardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        CardRepository.delete(Card);
    }

    @Transactional
    @CacheEvict(value = "service2:cards", allEntries = true)
    public void deleteAll() {
        CardRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        CardRepository.dropTable();
    }
}