package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.CardDtoWithId;
import com.meshakin.rest.dto.without.id.CardDtoWithoutId;
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
    public CardDtoWithId create(CardDtoWithoutId CardDtoWithoutId) {
        Card Card = cardMapper.toEntity(CardDtoWithoutId);
        Card savedCard = cardRepository.save(Card);

        return cardMapper.toDtoWithId(savedCard);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:Cards", key = "#id")
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
    @CachePut(value = "service3:Cards", key = "#dto.id")
    public CardDtoWithId update(CardDtoWithId CardDtoWithId) {
        Card Card = cardRepository.findById(CardDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Card.getVersion().equals(CardDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        cardMapper.updateEntity(CardDtoWithId, Card);
        cardRepository.flush();

        return cardMapper.toDtoWithId(Card);
    }

    @Transactional
    @CacheEvict(value = "service3:Cards", key = "#id")
    public void delete(Long id) {
        Card Card = cardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        cardRepository.delete(Card);
    }

    @Transactional
    @CacheEvict(value = "service3:Cards", allEntries = true)
    public void deleteAll() {
        cardRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        cardRepository.dropTable();
    }
}