package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.TransactionTypeDtoWithId;
import com.meshakin.rest.dto.no.id.TransactionTypeDtoWithoutId;
import com.meshakin.rest.entity.TransactionType;
import com.meshakin.rest.mapper.TransactionTypeMapper;
import com.meshakin.rest.repository.TransactionTypeRepository;
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
public class TransactionTypeService {
    private final TransactionTypeMapper transactionTypeMapper;
    private final TransactionTypeRepository TransactionTypeRepository;

    @Transactional
    public TransactionTypeDtoWithId create(TransactionTypeDtoWithoutId TransactionTypeDtoWithoutId) {
        TransactionType TransactionType = transactionTypeMapper.toEntity(TransactionTypeDtoWithoutId);
        TransactionType savedTransactionType = TransactionTypeRepository.save(TransactionType);

        return transactionTypeMapper.toDtoWithId(savedTransactionType);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:transactionTypes", key = "#id")
    public Optional<TransactionTypeDtoWithId> read(Long id) {

        Optional<TransactionTypeDtoWithId> maybeTransactionType = TransactionTypeRepository.findById(id)
                .map(transactionTypeMapper::toDtoWithId);

        return maybeTransactionType;
    }

    @Transactional(readOnly = true)
    public List<TransactionTypeDtoWithId> readAll() {
        return TransactionTypeRepository.findAll().stream()
                .map(transactionTypeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:transactionTypes", key = "#dto.id")
    public TransactionTypeDtoWithId update(TransactionTypeDtoWithId TransactionTypeDtoWithId) {
        TransactionType TransactionType = TransactionTypeRepository.findById(TransactionTypeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!TransactionType.getVersion().equals(TransactionTypeDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        transactionTypeMapper.updateEntity(TransactionTypeDtoWithId, TransactionType);
        TransactionTypeRepository.flush();

        return transactionTypeMapper.toDtoWithId(TransactionType);
    }

    @Transactional
    @CacheEvict(value = "service2:transactionTypes", key = "#id")
    public void delete(Long id) {
        TransactionType TransactionType = TransactionTypeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        TransactionTypeRepository.delete(TransactionType);
    }

    @Transactional
    @CacheEvict(value = "service2:transactionTypes", allEntries = true)
    public void deleteAll() {
        TransactionTypeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        TransactionTypeRepository.dropTable();
    }
}