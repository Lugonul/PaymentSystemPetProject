package com.meshakin.rest.service;

import com.meshakin.rest.dto.AcquiringBankDtoWithId;
import com.meshakin.rest.dto.AcquiringBankDtoWithoutId;
import com.meshakin.rest.entity.AcquiringBank;
import com.meshakin.rest.mapper.AcquiringBankMapper;
import com.meshakin.rest.repository.AcquiringBankRepository;
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
public class AcquiringBankService {
    private final AcquiringBankMapper acquiringBankMapper;
    private final AcquiringBankRepository acquiringBankRepository;

    @Transactional
    public AcquiringBankDtoWithId create(AcquiringBankDtoWithoutId acquiringBankDtoWithoutId) {
        AcquiringBank AcquiringBank = acquiringBankMapper.toEntity(acquiringBankDtoWithoutId);
        AcquiringBank savedAcquiringBank = acquiringBankRepository.save(AcquiringBank);

        return acquiringBankMapper.toDtoWithId(savedAcquiringBank);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:AcquiringBanks", key = "#id")
    public Optional<AcquiringBankDtoWithId> read(Long id) {

        Optional<AcquiringBankDtoWithId> maybeAcquiringBank = acquiringBankRepository.findById(id)
                .map(acquiringBankMapper::toDtoWithId);

        return maybeAcquiringBank;
    }

    @Transactional(readOnly = true)
    public List<AcquiringBankDtoWithId> readAll() {
        return acquiringBankRepository.findAll().stream()
                .map(acquiringBankMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:AcquiringBanks", key = "#dto.id")
    public AcquiringBankDtoWithId update(AcquiringBankDtoWithId acquiringBankDtoWithId) {
        AcquiringBank AcquiringBank = acquiringBankRepository.findById(acquiringBankDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!AcquiringBank.getVersion().equals(acquiringBankDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        acquiringBankMapper.updateEntity(acquiringBankDtoWithId, AcquiringBank);
        acquiringBankRepository.flush();

        return acquiringBankMapper.toDtoWithId(AcquiringBank);
    }

    @Transactional
    @CacheEvict(value = "service1:AcquiringBanks", key = "#id")
    public void delete(Long id) {
        AcquiringBank AcquiringBank = acquiringBankRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        acquiringBankRepository.delete(AcquiringBank);
    }

    @Transactional
    @CacheEvict(value = "service1:AcquiringBanks", allEntries = true)
    public void deleteAll() {
        acquiringBankRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        acquiringBankRepository.dropTable();
    }
}