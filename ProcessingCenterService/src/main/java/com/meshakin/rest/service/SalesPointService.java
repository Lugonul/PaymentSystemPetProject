package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.SalesPointDtoWithId;
import com.meshakin.rest.dto.no.id.SalesPointDtoWithoutId;
import com.meshakin.rest.entity.SalesPoint;
import com.meshakin.rest.mapper.SalesPointMapper;
import com.meshakin.rest.repository.SalesPointRepository;
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
public class SalesPointService {
    private final SalesPointMapper salesPointMapper;
    private final SalesPointRepository SalesPointRepository;

    @Transactional
    public SalesPointDtoWithId create(SalesPointDtoWithoutId SalesPointDtoWithoutId) {
        SalesPoint SalesPoint = salesPointMapper.toEntity(SalesPointDtoWithoutId);
        SalesPoint savedSalesPoint = SalesPointRepository.save(SalesPoint);

        return salesPointMapper.toDtoWithId(savedSalesPoint);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:salesPoints", key = "#id")
    public Optional<SalesPointDtoWithId> read(Long id) {

        Optional<SalesPointDtoWithId> maybeSalesPoint = SalesPointRepository.findById(id)
                .map(salesPointMapper::toDtoWithId);

        return maybeSalesPoint;
    }

    @Transactional(readOnly = true)
    public List<SalesPointDtoWithId> readAll() {
        return SalesPointRepository.findAll().stream()
                .map(salesPointMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:salesPoints", key = "#dto.id")
    public SalesPointDtoWithId update(SalesPointDtoWithId SalesPointDtoWithId) {
        SalesPoint SalesPoint = SalesPointRepository.findById(SalesPointDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!SalesPoint.getVersion().equals(SalesPointDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        salesPointMapper.updateEntity(SalesPointDtoWithId, SalesPoint);
        SalesPointRepository.flush();

        return salesPointMapper.toDtoWithId(SalesPoint);
    }

    @Transactional
    @CacheEvict(value = "service2:salesPoints", key = "#id")
    public void delete(Long id) {
        SalesPoint SalesPoint = SalesPointRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        SalesPointRepository.delete(SalesPoint);
    }

    @Transactional
    @CacheEvict(value = "service2:salesPoints", allEntries = true)
    public void deleteAll() {
        SalesPointRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        SalesPointRepository.dropTable();
    }
}