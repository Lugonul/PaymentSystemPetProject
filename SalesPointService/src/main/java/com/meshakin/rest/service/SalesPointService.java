package com.meshakin.rest.service;

import com.meshakin.rest.dto.SalesPointDtoWithId;
import com.meshakin.rest.dto.SalesPointDtoWithoutId;
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
    private final SalesPointRepository salesPointRepository;

    @Transactional
    public SalesPointDtoWithId create(SalesPointDtoWithoutId salesPointDtoWithoutId) {
        SalesPoint SalesPoint = salesPointMapper.toEntity(salesPointDtoWithoutId);
        SalesPoint savedSalesPoint = salesPointRepository.save(SalesPoint);

        return salesPointMapper.toDtoWithId(savedSalesPoint);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:SalesPoints", key = "#id")
    public Optional<SalesPointDtoWithId> read(Long id) {

        Optional<SalesPointDtoWithId> maybeSalesPoint = salesPointRepository.findById(id)
                .map(salesPointMapper::toDtoWithId);

        return maybeSalesPoint;
    }

    @Transactional(readOnly = true)
    public List<SalesPointDtoWithId> readAll() {
        return salesPointRepository.findAll().stream()
                .map(salesPointMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:SalesPoints", key = "#dto.id")
    public SalesPointDtoWithId update(SalesPointDtoWithId salesPointDtoWithId) {
        SalesPoint SalesPoint = salesPointRepository.findById(salesPointDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!SalesPoint.getVersion().equals(salesPointDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        salesPointMapper.updateEntity(salesPointDtoWithId, SalesPoint);
        salesPointRepository.flush();

        return salesPointMapper.toDtoWithId(SalesPoint);
    }

    @Transactional
    @CacheEvict(value = "service1:SalesPoints", key = "#id")
    public void delete(Long id) {
        SalesPoint SalesPoint = salesPointRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        salesPointRepository.delete(SalesPoint);
    }

    @Transactional
    @CacheEvict(value = "service1:SalesPoints", allEntries = true)
    public void deleteAll() {
        salesPointRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        salesPointRepository.dropTable();
    }
}