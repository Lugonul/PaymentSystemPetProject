package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.TransactionTypeDtoWithId;
import com.meshakin.rest.dto.without.id.TransactionTypeDtoWithoutId;
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
    private final TransactionTypeRepository transactionTypeRepository;

    @Transactional
    public TransactionTypeDtoWithId create(TransactionTypeDtoWithoutId TransactionTypeDtoWithoutId) {
        TransactionType TransactionType = transactionTypeMapper.toEntity(TransactionTypeDtoWithoutId);
        TransactionType savedTransactionType = transactionTypeRepository.save(TransactionType);

        return transactionTypeMapper.toDtoWithId(savedTransactionType);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:TransactionTypes", key = "#id")
    public Optional<TransactionTypeDtoWithId> read(Long id) {

        Optional<TransactionTypeDtoWithId> maybeTransactionType = transactionTypeRepository.findById(id)
                .map(transactionTypeMapper::toDtoWithId);

        return maybeTransactionType;
    }

    @Transactional(readOnly = true)
    public List<TransactionTypeDtoWithId> readAll() {
        return transactionTypeRepository.findAll().stream()
                .map(transactionTypeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:TransactionTypes", key = "#dto.id")
    public TransactionTypeDtoWithId update(TransactionTypeDtoWithId TransactionTypeDtoWithId) {
        TransactionType TransactionType = transactionTypeRepository.findById(TransactionTypeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!TransactionType.getVersion().equals(TransactionTypeDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        transactionTypeMapper.updateEntity(TransactionTypeDtoWithId, TransactionType);
        transactionTypeRepository.flush();

        return transactionTypeMapper.toDtoWithId(TransactionType);
    }

    @Transactional
    @CacheEvict(value = "service3:TransactionTypes", key = "#id")
    public void delete(Long id) {
        TransactionType TransactionType = transactionTypeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        transactionTypeRepository.delete(TransactionType);
    }

    @Transactional
    @CacheEvict(value = "service3:TransactionTypes", allEntries = true)
    public void deleteAll() {
        transactionTypeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        transactionTypeRepository.dropTable();
    }
}