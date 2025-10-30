package com.meshakin.rest.service;

import com.meshakin.rest.dto.MerchantCategoryCodeDtoWithId;
import com.meshakin.rest.dto.MerchantCategoryCodeDtoWithoutId;
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
    private final MerchantCategoryCodeRepository merchantCategoryCodeRepository;

    @Transactional
    public MerchantCategoryCodeDtoWithId create(MerchantCategoryCodeDtoWithoutId merchantCategoryCodeDtoWithoutId) {
        MerchantCategoryCode MerchantCategoryCode = merchantCategoryCodeMapper.toEntity(merchantCategoryCodeDtoWithoutId);
        MerchantCategoryCode savedMerchantCategoryCode = merchantCategoryCodeRepository.save(MerchantCategoryCode);

        return merchantCategoryCodeMapper.toDtoWithId(savedMerchantCategoryCode);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:MerchantCategoryCodes", key = "#id")
    public Optional<MerchantCategoryCodeDtoWithId> read(Long id) {

        Optional<MerchantCategoryCodeDtoWithId> maybeMerchantCategoryCode = merchantCategoryCodeRepository.findById(id)
                .map(merchantCategoryCodeMapper::toDtoWithId);

        return maybeMerchantCategoryCode;
    }

    @Transactional(readOnly = true)
    public List<MerchantCategoryCodeDtoWithId> readAll() {
        return merchantCategoryCodeRepository.findAll().stream()
                .map(merchantCategoryCodeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:MerchantCategoryCodes", key = "#dto.id")
    public MerchantCategoryCodeDtoWithId update(MerchantCategoryCodeDtoWithId merchantCategoryCodeDtoWithId) {
        MerchantCategoryCode MerchantCategoryCode = merchantCategoryCodeRepository.findById(merchantCategoryCodeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!MerchantCategoryCode.getVersion().equals(merchantCategoryCodeDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        merchantCategoryCodeMapper.updateEntity(merchantCategoryCodeDtoWithId, MerchantCategoryCode);
        merchantCategoryCodeRepository.flush();

        return merchantCategoryCodeMapper.toDtoWithId(MerchantCategoryCode);
    }

    @Transactional
    @CacheEvict(value = "service1:MerchantCategoryCodes", key = "#id")
    public void delete(Long id) {
        MerchantCategoryCode MerchantCategoryCode = merchantCategoryCodeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        merchantCategoryCodeRepository.delete(MerchantCategoryCode);
    }

    @Transactional
    @CacheEvict(value = "service1:MerchantCategoryCodes", allEntries = true)
    public void deleteAll() {
        merchantCategoryCodeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        merchantCategoryCodeRepository.dropTable();
    }
}