package com.meshakin.rest.service;

import com.meshakin.rest.dto.IssuingBankDtoWithId;
import com.meshakin.rest.dto.IssuingBankDtoWithoutId;
import com.meshakin.rest.entity.IssuingBank;
import com.meshakin.rest.mapper.IssuingBankMapper;
import com.meshakin.rest.repository.IssuingBankRepository;
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
public class IssuingBankService {
    private final IssuingBankMapper issuingBankMapper;
    private final IssuingBankRepository issuingBankRepository;

    @Transactional
    public IssuingBankDtoWithId create(IssuingBankDtoWithoutId issuingBankDtoWithoutId) {
        IssuingBank IssuingBank = issuingBankMapper.toEntity(issuingBankDtoWithoutId);
        IssuingBank savedIssuingBank = issuingBankRepository.save(IssuingBank);

        return issuingBankMapper.toDtoWithId(savedIssuingBank);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:IssuingBanks", key = "#id")
    public Optional<IssuingBankDtoWithId> read(Long id) {

        Optional<IssuingBankDtoWithId> maybeIssuingBank = issuingBankRepository.findById(id)
                .map(issuingBankMapper::toDtoWithId);

        return maybeIssuingBank;
    }

    @Transactional(readOnly = true)
    public List<IssuingBankDtoWithId> readAll() {
        return issuingBankRepository.findAll().stream()
                .map(issuingBankMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:IssuingBanks", key = "#dto.id")
    public IssuingBankDtoWithId update(IssuingBankDtoWithId issuingBankDtoWithId) {
        IssuingBank IssuingBank = issuingBankRepository.findById(issuingBankDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!IssuingBank.getVersion().equals(issuingBankDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        issuingBankMapper.updateEntity(issuingBankDtoWithId, IssuingBank);
        issuingBankRepository.flush();

        return issuingBankMapper.toDtoWithId(IssuingBank);
    }

    @Transactional
    @CacheEvict(value = "service1:IssuingBanks", key = "#id")
    public void delete(Long id) {
        IssuingBank IssuingBank = issuingBankRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        issuingBankRepository.delete(IssuingBank);
    }

    @Transactional
    @CacheEvict(value = "service1:IssuingBanks", allEntries = true)
    public void deleteAll() {
        issuingBankRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        issuingBankRepository.dropTable();
    }
}