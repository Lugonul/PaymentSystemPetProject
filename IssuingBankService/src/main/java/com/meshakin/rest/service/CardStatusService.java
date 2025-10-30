package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.CardStatusDtoWithId;
import com.meshakin.rest.dto.without.id.CardStatusDtoWithoutId;
import com.meshakin.rest.entity.CardStatus;
import com.meshakin.rest.mapper.CardStatusMapper;
import com.meshakin.rest.repository.CardStatusRepository;
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
public class CardStatusService {
    private final CardStatusMapper cardStatusMapper;
    private final CardStatusRepository cardStatusRepository;

    @Transactional
    public CardStatusDtoWithId create(CardStatusDtoWithoutId CardStatusDtoWithoutId) {
        CardStatus CardStatus = cardStatusMapper.toEntity(CardStatusDtoWithoutId);
        CardStatus savedCardStatus = cardStatusRepository.save(CardStatus);

        return cardStatusMapper.toDtoWithId(savedCardStatus);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:CardStatuss", key = "#id")
    public Optional<CardStatusDtoWithId> read(Long id) {

        Optional<CardStatusDtoWithId> maybeCardStatus = cardStatusRepository.findById(id)
                .map(cardStatusMapper::toDtoWithId);

        return maybeCardStatus;
    }

    @Transactional(readOnly = true)
    public List<CardStatusDtoWithId> readAll() {
        return cardStatusRepository.findAll().stream()
                .map(cardStatusMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:CardStatuss", key = "#dto.id")
    public CardStatusDtoWithId update(CardStatusDtoWithId CardStatusDtoWithId) {
        CardStatus CardStatus = cardStatusRepository.findById(CardStatusDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!CardStatus.getVersion().equals(CardStatusDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        cardStatusMapper.updateEntity(CardStatusDtoWithId, CardStatus);
        cardStatusRepository.flush();

        return cardStatusMapper.toDtoWithId(CardStatus);
    }

    @Transactional
    @CacheEvict(value = "service3:CardStatuss", key = "#id")
    public void delete(Long id) {
        CardStatus CardStatus = cardStatusRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        cardStatusRepository.delete(CardStatus);
    }

    @Transactional
    @CacheEvict(value = "service3:CardStatuss", allEntries = true)
    public void deleteAll() {
        cardStatusRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        cardStatusRepository.dropTable();
    }
}