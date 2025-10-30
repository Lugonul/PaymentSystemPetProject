package com.meshakin.rest.service;

import com.meshakin.rest.dto.CurrencyDtoWithId;
import com.meshakin.rest.dto.CurrencyDtoWithoutId;
import com.meshakin.rest.entity.Currency;
import com.meshakin.rest.mapper.CurrencyMapper;
import com.meshakin.rest.repository.CurrencyRepository;
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
public class CurrencyService {
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public CurrencyDtoWithId create(CurrencyDtoWithoutId currencyDtoWithoutId) {
        Currency Currency = currencyMapper.toEntity(currencyDtoWithoutId);
        Currency savedCurrency = currencyRepository.save(Currency);

        return currencyMapper.toDtoWithId(savedCurrency);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:Currencys", key = "#id")
    public Optional<CurrencyDtoWithId> read(Long id) {

        Optional<CurrencyDtoWithId> maybeCurrency = currencyRepository.findById(id)
                .map(currencyMapper::toDtoWithId);

        return maybeCurrency;
    }

    @Transactional(readOnly = true)
    public List<CurrencyDtoWithId> readAll() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:Currencys", key = "#dto.id")
    public CurrencyDtoWithId update(CurrencyDtoWithId currencyDtoWithId) {
        Currency Currency = currencyRepository.findById(currencyDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Currency.getVersion().equals(currencyDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        currencyMapper.updateEntity(currencyDtoWithId, Currency);
        currencyRepository.flush();

        return currencyMapper.toDtoWithId(Currency);
    }

    @Transactional
    @CacheEvict(value = "service1:Currencys", key = "#id")
    public void delete(Long id) {
        Currency Currency = currencyRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        currencyRepository.delete(Currency);
    }

    @Transactional
    @CacheEvict(value = "service1:Currencys", allEntries = true)
    public void deleteAll() {
        currencyRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        currencyRepository.dropTable();
    }
}