package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.PaymentSystemDtoWithId;
import com.meshakin.rest.dto.without.id.PaymentSystemDtoWithoutId;
import com.meshakin.rest.entity.PaymentSystem;
import com.meshakin.rest.mapper.PaymentSystemMapper;
import com.meshakin.rest.repository.PaymentSystemRepository;
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
public class PaymentSystemService {
    private final PaymentSystemMapper paymentSystemMapper;
    private final PaymentSystemRepository paymentSystemRepository;

    @Transactional
    public PaymentSystemDtoWithId create(PaymentSystemDtoWithoutId PaymentSystemDtoWithoutId) {
        PaymentSystem PaymentSystem = paymentSystemMapper.toEntity(PaymentSystemDtoWithoutId);
        PaymentSystem savedPaymentSystem = paymentSystemRepository.save(PaymentSystem);

        return paymentSystemMapper.toDtoWithId(savedPaymentSystem);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:PaymentSystems", key = "#id")
    public Optional<PaymentSystemDtoWithId> read(Long id) {

        Optional<PaymentSystemDtoWithId> maybePaymentSystem = paymentSystemRepository.findById(id)
                .map(paymentSystemMapper::toDtoWithId);

        return maybePaymentSystem;
    }

    @Transactional(readOnly = true)
    public List<PaymentSystemDtoWithId> readAll() {
        return paymentSystemRepository.findAll().stream()
                .map(paymentSystemMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:PaymentSystems", key = "#dto.id")
    public PaymentSystemDtoWithId update(PaymentSystemDtoWithId PaymentSystemDtoWithId) {
        PaymentSystem PaymentSystem = paymentSystemRepository.findById(PaymentSystemDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!PaymentSystem.getVersion().equals(PaymentSystemDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        paymentSystemMapper.updateEntity(PaymentSystemDtoWithId, PaymentSystem);
        paymentSystemRepository.flush();

        return paymentSystemMapper.toDtoWithId(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service3:PaymentSystems", key = "#id")
    public void delete(Long id) {
        PaymentSystem PaymentSystem = paymentSystemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        paymentSystemRepository.delete(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service3:PaymentSystems", allEntries = true)
    public void deleteAll() {
        paymentSystemRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        paymentSystemRepository.dropTable();
    }
}