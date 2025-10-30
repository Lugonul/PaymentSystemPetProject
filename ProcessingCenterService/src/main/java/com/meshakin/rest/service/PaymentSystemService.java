package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.PaymentSystemDtoWithId;
import com.meshakin.rest.dto.no.id.PaymentSystemDtoWithoutId;
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
    private final PaymentSystemRepository PaymentSystemRepository;

    @Transactional
    public PaymentSystemDtoWithId create(PaymentSystemDtoWithoutId PaymentSystemDtoWithoutId) {
        PaymentSystem PaymentSystem = paymentSystemMapper.toEntity(PaymentSystemDtoWithoutId);
        PaymentSystem savedPaymentSystem = PaymentSystemRepository.save(PaymentSystem);

        return paymentSystemMapper.toDtoWithId(savedPaymentSystem);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:paymentSystems", key = "#id")
    public Optional<PaymentSystemDtoWithId> read(Long id) {

        Optional<PaymentSystemDtoWithId> maybePaymentSystem = PaymentSystemRepository.findById(id)
                .map(paymentSystemMapper::toDtoWithId);

        return maybePaymentSystem;
    }

    @Transactional(readOnly = true)
    public List<PaymentSystemDtoWithId> readAll() {
        return PaymentSystemRepository.findAll().stream()
                .map(paymentSystemMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:paymentSystems", key = "#dto.id")
    public PaymentSystemDtoWithId update(PaymentSystemDtoWithId PaymentSystemDtoWithId) {
        PaymentSystem PaymentSystem = PaymentSystemRepository.findById(PaymentSystemDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!PaymentSystem.getVersion().equals(PaymentSystemDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        paymentSystemMapper.updateEntity(PaymentSystemDtoWithId, PaymentSystem);
        PaymentSystemRepository.flush();

        return paymentSystemMapper.toDtoWithId(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service2:paymentSystems", key = "#id")
    public void delete(Long id) {
        PaymentSystem PaymentSystem = PaymentSystemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        PaymentSystemRepository.delete(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service2:paymentSystems", allEntries = true)
    public void deleteAll() {
        PaymentSystemRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        PaymentSystemRepository.dropTable();
    }
}