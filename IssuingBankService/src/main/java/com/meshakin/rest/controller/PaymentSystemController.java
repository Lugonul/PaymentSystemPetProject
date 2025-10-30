package com.meshakin.rest.controller;

import com.meshakin.rest.dto.with.id.PaymentSystemDtoWithId;
import com.meshakin.rest.dto.without.id.PaymentSystemDtoWithoutId;
import com.meshakin.rest.service.PaymentSystemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/paymentSystems")
public class PaymentSystemController {

    private final PaymentSystemService paymentSystemService;

    @GetMapping
    public ResponseEntity<List<PaymentSystemDtoWithId>> getAllPaymentSystems() {
        return ResponseEntity.ok(paymentSystemService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSystemDtoWithId> getPaymentSystemById(
            @PathVariable("id") Long id) {
        return paymentSystemService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentSystemDtoWithId> savePaymentSystem(@Valid @RequestBody PaymentSystemDtoWithoutId paymentSystemDtoWithoutId) {
        PaymentSystemDtoWithId paymentSystemDtoWithId = paymentSystemService.create(paymentSystemDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paymentSystemDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(paymentSystemDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystemDtoWithId> updatePaymentSystem(@Valid @RequestBody PaymentSystemDtoWithId paymentSystemDtoWithId) {
        try {
            PaymentSystemDtoWithId updatedDto = paymentSystemService.update(paymentSystemDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentSystem(@PathVariable("id") Long id){
        try {
            paymentSystemService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}