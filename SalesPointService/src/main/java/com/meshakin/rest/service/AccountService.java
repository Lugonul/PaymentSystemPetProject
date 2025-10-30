package com.meshakin.rest.service;

import com.meshakin.rest.dto.AccountDtoWithId;
import com.meshakin.rest.dto.AccountDtoWithoutId;
import com.meshakin.rest.entity.Account;
import com.meshakin.rest.mapper.AccountMapper;
import com.meshakin.rest.repository.AccountRepository;
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
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountDtoWithId create(AccountDtoWithoutId accountDtoWithoutId) {
        Account Account = accountMapper.toEntity(accountDtoWithoutId);
        Account savedAccount = accountRepository.save(Account);

        return accountMapper.toDtoWithId(savedAccount);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:Accounts", key = "#id")
    public Optional<AccountDtoWithId> read(Long id) {

        Optional<AccountDtoWithId> maybeAccount = accountRepository.findById(id)
                .map(accountMapper::toDtoWithId);

        return maybeAccount;
    }

    @Transactional(readOnly = true)
    public List<AccountDtoWithId> readAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:Accounts", key = "#dto.id")
    public AccountDtoWithId update(AccountDtoWithId accountDtoWithId) {
        Account Account = accountRepository.findById(accountDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Account.getVersion().equals(accountDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        accountMapper.updateEntity(accountDtoWithId, Account);
        accountRepository.flush();

        return accountMapper.toDtoWithId(Account);
    }

    @Transactional
    @CacheEvict(value = "service1:Accounts", key = "#id")
    public void delete(Long id) {
        Account Account = accountRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        accountRepository.delete(Account);
    }

    @Transactional
    @CacheEvict(value = "service1:Accounts", allEntries = true)
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        accountRepository.dropTable();
    }
}