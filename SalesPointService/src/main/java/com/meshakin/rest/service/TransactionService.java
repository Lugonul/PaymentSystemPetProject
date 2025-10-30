package com.meshakin.rest.service;

import com.meshakin.rest.dto.TransactionDtoWithId;
import com.meshakin.rest.dto.TransactionDtoWithoutId;
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
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionDtoWithId create(TransactionDtoWithoutId transactionDtoWithoutId) {
        Transaction Transaction = transactionMapper.toEntity(transactionDtoWithoutId);
        Transaction savedTransaction = transactionRepository.save(Transaction);

        return transactionMapper.toDtoWithId(savedTransaction);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:Transactions", key = "#id")
    public Optional<TransactionDtoWithId> read(Long id) {

        Optional<TransactionDtoWithId> maybeTransaction = transactionRepository.findById(id)
                .map(transactionMapper::toDtoWithId);

        return maybeTransaction;
    }

    @Transactional(readOnly = true)
    public List<TransactionDtoWithId> readAll() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:Transactions", key = "#dto.id")
    public TransactionDtoWithId update(TransactionDtoWithId transactionDtoWithId) {
        Transaction Transaction = transactionRepository.findById(transactionDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Transaction.getVersion().equals(transactionDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        transactionMapper.updateEntity(transactionDtoWithId, Transaction);
        transactionRepository.flush();

        return transactionMapper.toDtoWithId(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service1:Transactions", key = "#id")
    public void delete(Long id) {
        Transaction Transaction = transactionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        transactionRepository.delete(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service1:Transactions", allEntries = true)
    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        transactionRepository.dropTable();
    }
}