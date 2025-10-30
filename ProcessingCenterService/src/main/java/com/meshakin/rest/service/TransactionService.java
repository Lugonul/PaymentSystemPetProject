package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.TransactionDtoWithId;
import com.meshakin.rest.dto.no.id.TransactionDtoWithoutId;
import com.meshakin.rest.entity.Transaction;
import com.meshakin.rest.mapper.TransactionMapper;
import com.meshakin.rest.repository.TransactionRepository;
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
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository TransactionRepository;

    @Transactional
    public TransactionDtoWithId create(TransactionDtoWithoutId TransactionDtoWithoutId) {
        Transaction Transaction = transactionMapper.toEntity(TransactionDtoWithoutId);
        Transaction savedTransaction = TransactionRepository.save(Transaction);

        return transactionMapper.toDtoWithId(savedTransaction);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:transactions", key = "#id")
    public Optional<TransactionDtoWithId> read(Long id) {

        Optional<TransactionDtoWithId> maybeTransaction = TransactionRepository.findById(id)
                .map(transactionMapper::toDtoWithId);

        return maybeTransaction;
    }

    @Transactional(readOnly = true)
    public List<TransactionDtoWithId> readAll() {
        return TransactionRepository.findAll().stream()
                .map(transactionMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:transactions", key = "#dto.id")
    public TransactionDtoWithId update(TransactionDtoWithId TransactionDtoWithId) {
        Transaction Transaction = TransactionRepository.findById(TransactionDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Transaction.getVersion().equals(TransactionDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        transactionMapper.updateEntity(TransactionDtoWithId, Transaction);
        TransactionRepository.flush();

        return transactionMapper.toDtoWithId(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service2:transactions", key = "#id")
    public void delete(Long id) {
        Transaction Transaction = TransactionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        TransactionRepository.delete(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service2:transactions", allEntries = true)
    public void deleteAll() {
        TransactionRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        TransactionRepository.dropTable();
    }
}