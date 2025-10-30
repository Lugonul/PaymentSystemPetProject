package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.AccountTypeDtoWithId;
import com.meshakin.rest.dto.without.id.AccountTypeDtoWithoutId;
import com.meshakin.rest.entity.AccountType;
import com.meshakin.rest.mapper.AccountTypeMapper;
import com.meshakin.rest.repository.AccountTypeRepository;
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
public class AccountTypeService {
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeRepository accountTypeRepository;

    @Transactional
    public AccountTypeDtoWithId create(AccountTypeDtoWithoutId AccountTypeDtoWithoutId) {
        AccountType AccountType = accountTypeMapper.toEntity(AccountTypeDtoWithoutId);
        AccountType savedAccountType = accountTypeRepository.save(AccountType);

        return accountTypeMapper.toDtoWithId(savedAccountType);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:AccountTypes", key = "#id")
    public Optional<AccountTypeDtoWithId> read(Long id) {

        Optional<AccountTypeDtoWithId> maybeAccountType = accountTypeRepository.findById(id)
                .map(accountTypeMapper::toDtoWithId);

        return maybeAccountType;
    }

    @Transactional(readOnly = true)
    public List<AccountTypeDtoWithId> readAll() {
        return accountTypeRepository.findAll().stream()
                .map(accountTypeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:AccountTypes", key = "#dto.id")
    public AccountTypeDtoWithId update(AccountTypeDtoWithId AccountTypeDtoWithId) {
        AccountType AccountType = accountTypeRepository.findById(AccountTypeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!AccountType.getVersion().equals(AccountTypeDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        accountTypeMapper.updateEntity(AccountTypeDtoWithId, AccountType);
        accountTypeRepository.flush();

        return accountTypeMapper.toDtoWithId(AccountType);
    }

    @Transactional
    @CacheEvict(value = "service3:AccountTypes", key = "#id")
    public void delete(Long id) {
        AccountType AccountType = accountTypeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        accountTypeRepository.delete(AccountType);
    }

    @Transactional
    @CacheEvict(value = "service3:AccountTypes", allEntries = true)
    public void deleteAll() {
        accountTypeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        accountTypeRepository.dropTable();
    }
}