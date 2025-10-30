package com.meshakin.rest.service;

import com.meshakin.rest.dto.PaymentSystemDtoWithId;
import com.meshakin.rest.dto.PaymentSystemDtoWithoutId;
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
    public PaymentSystemDtoWithId create(PaymentSystemDtoWithoutId paymentSystemDtoWithoutId) {
        PaymentSystem PaymentSystem = paymentSystemMapper.toEntity(paymentSystemDtoWithoutId);
        PaymentSystem savedPaymentSystem = paymentSystemRepository.save(PaymentSystem);

        return paymentSystemMapper.toDtoWithId(savedPaymentSystem);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:PaymentSystems", key = "#id")
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
    @CachePut(value = "service1:PaymentSystems", key = "#dto.id")
    public PaymentSystemDtoWithId update(PaymentSystemDtoWithId paymentSystemDtoWithId) {
        PaymentSystem PaymentSystem = paymentSystemRepository.findById(paymentSystemDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!PaymentSystem.getVersion().equals(paymentSystemDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        paymentSystemMapper.updateEntity(paymentSystemDtoWithId, PaymentSystem);
        paymentSystemRepository.flush();

        return paymentSystemMapper.toDtoWithId(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service1:PaymentSystems", key = "#id")
    public void delete(Long id) {
        PaymentSystem PaymentSystem = paymentSystemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        paymentSystemRepository.delete(PaymentSystem);
    }

    @Transactional
    @CacheEvict(value = "service1:PaymentSystems", allEntries = true)
    public void deleteAll() {
        paymentSystemRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        paymentSystemRepository.dropTable();
    }
}