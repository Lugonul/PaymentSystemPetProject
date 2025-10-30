package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.MerchantCategoryCodeDtoWithId;
import com.meshakin.rest.dto.no.id.MerchantCategoryCodeDtoWithoutId;
import com.meshakin.rest.entity.MerchantCategoryCode;
import com.meshakin.rest.mapper.MerchantCategoryCodeMapper;
import com.meshakin.rest.repository.MerchantCategoryCodeRepository;
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
public class MerchantCategoryCodeService {
    private final MerchantCategoryCodeMapper merchantCategoryCodeMapper;
    private final MerchantCategoryCodeRepository MerchantCategoryCodeRepository;

    @Transactional
    public MerchantCategoryCodeDtoWithId create(MerchantCategoryCodeDtoWithoutId MerchantCategoryCodeDtoWithoutId) {
        MerchantCategoryCode MerchantCategoryCode = merchantCategoryCodeMapper.toEntity(MerchantCategoryCodeDtoWithoutId);
        MerchantCategoryCode savedMerchantCategoryCode = MerchantCategoryCodeRepository.save(MerchantCategoryCode);

        return merchantCategoryCodeMapper.toDtoWithId(savedMerchantCategoryCode);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:merchantCategoryCodes", key = "#id")
    public Optional<MerchantCategoryCodeDtoWithId> read(Long id) {

        Optional<MerchantCategoryCodeDtoWithId> maybeMerchantCategoryCode = MerchantCategoryCodeRepository.findById(id)
                .map(merchantCategoryCodeMapper::toDtoWithId);

        return maybeMerchantCategoryCode;
    }

    @Transactional(readOnly = true)
    public List<MerchantCategoryCodeDtoWithId> readAll() {
        return MerchantCategoryCodeRepository.findAll().stream()
                .map(merchantCategoryCodeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:merchantCategoryCodes", key = "#dto.id")
    public MerchantCategoryCodeDtoWithId update(MerchantCategoryCodeDtoWithId MerchantCategoryCodeDtoWithId) {
        MerchantCategoryCode MerchantCategoryCode = MerchantCategoryCodeRepository.findById(MerchantCategoryCodeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!MerchantCategoryCode.getVersion().equals(MerchantCategoryCodeDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        merchantCategoryCodeMapper.updateEntity(MerchantCategoryCodeDtoWithId, MerchantCategoryCode);
        MerchantCategoryCodeRepository.flush();

        return merchantCategoryCodeMapper.toDtoWithId(MerchantCategoryCode);
    }

    @Transactional
    @CacheEvict(value = "service2:merchantCategoryCodes", key = "#id")
    public void delete(Long id) {
        MerchantCategoryCode MerchantCategoryCode = MerchantCategoryCodeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        MerchantCategoryCodeRepository.delete(MerchantCategoryCode);
    }

    @Transactional
    @CacheEvict(value = "service2:merchantCategoryCodes", allEntries = true)
    public void deleteAll() {
        MerchantCategoryCodeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        MerchantCategoryCodeRepository.dropTable();
    }
}