package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.BankSettingDtoWithId;
import com.meshakin.rest.dto.without.id.BankSettingDtoWithoutId;
import com.meshakin.rest.entity.BankSetting;
import com.meshakin.rest.mapper.BankSettingMapper;
import com.meshakin.rest.repository.BankSettingRepository;
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
public class BankSettingService {
    private final BankSettingMapper bankSettingMapper;
    private final BankSettingRepository bankSettingRepository;

    @Transactional
    public BankSettingDtoWithId create(BankSettingDtoWithoutId BankSettingDtoWithoutId) {
        BankSetting BankSetting = bankSettingMapper.toEntity(BankSettingDtoWithoutId);
        BankSetting savedBankSetting = bankSettingRepository.save(BankSetting);

        return bankSettingMapper.toDtoWithId(savedBankSetting);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:BankSettings", key = "#id")
    public Optional<BankSettingDtoWithId> read(Long id) {

        Optional<BankSettingDtoWithId> maybeBankSetting = bankSettingRepository.findById(id)
                .map(bankSettingMapper::toDtoWithId);

        return maybeBankSetting;
    }

    @Transactional(readOnly = true)
    public List<BankSettingDtoWithId> readAll() {
        return bankSettingRepository.findAll().stream()
                .map(bankSettingMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:BankSettings", key = "#dto.id")
    public BankSettingDtoWithId update(BankSettingDtoWithId BankSettingDtoWithId) {
        BankSetting BankSetting = bankSettingRepository.findById(BankSettingDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!BankSetting.getVersion().equals(BankSettingDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        bankSettingMapper.updateEntity(BankSettingDtoWithId, BankSetting);
        bankSettingRepository.flush();

        return bankSettingMapper.toDtoWithId(BankSetting);
    }

    @Transactional
    @CacheEvict(value = "service3:BankSettings", key = "#id")
    public void delete(Long id) {
        BankSetting BankSetting = bankSettingRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        bankSettingRepository.delete(BankSetting);
    }

    @Transactional
    @CacheEvict(value = "service3:BankSettings", allEntries = true)
    public void deleteAll() {
        bankSettingRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        bankSettingRepository.dropTable();
    }
}