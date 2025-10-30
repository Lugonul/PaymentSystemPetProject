package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.TransactionDtoWithId;
import com.meshakin.rest.dto.without.id.TransactionDtoWithoutId;
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
    public TransactionDtoWithId create(TransactionDtoWithoutId TransactionDtoWithoutId) {
        Transaction Transaction = transactionMapper.toEntity(TransactionDtoWithoutId);
        Transaction savedTransaction = transactionRepository.save(Transaction);

        return transactionMapper.toDtoWithId(savedTransaction);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:Transactions", key = "#id")
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
    @CachePut(value = "service3:Transactions", key = "#dto.id")
    public TransactionDtoWithId update(TransactionDtoWithId TransactionDtoWithId) {
        Transaction Transaction = transactionRepository.findById(TransactionDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Transaction.getVersion().equals(TransactionDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        transactionMapper.updateEntity(TransactionDtoWithId, Transaction);
        transactionRepository.flush();

        return transactionMapper.toDtoWithId(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service3:Transactions", key = "#id")
    public void delete(Long id) {
        Transaction Transaction = transactionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        transactionRepository.delete(Transaction);
    }

    @Transactional
    @CacheEvict(value = "service3:Transactions", allEntries = true)
    public void deleteAll() {
        transactionRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        transactionRepository.dropTable();
    }
}