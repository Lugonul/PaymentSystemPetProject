package com.meshakin.rest.controller;

import com.meshakin.rest.dto.TransactionTypeDtoWithId;
import com.meshakin.rest.dto.TransactionTypeDtoWithoutId;
import com.meshakin.rest.service.TransactionTypeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/transactionTypes")
@RequiredArgsConstructor
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

    @GetMapping
    public ResponseEntity<List<TransactionTypeDtoWithId>> getAllTransactionTypes() {
        List<TransactionTypeDtoWithId> transactionTypes = transactionTypeService.readAll();
        return ResponseEntity.ok(transactionTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionTypeDtoWithId> getTransactionTypeById(@PathVariable("id") Long id) {
        return transactionTypeService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionTypeDtoWithId> createTransactionType(
            @Valid @RequestBody TransactionTypeDtoWithoutId transactionTypeDtoWithoutId) {
        TransactionTypeDtoWithId transactionTypeDtoWithId = transactionTypeService.create(transactionTypeDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionTypeDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(transactionTypeDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionTypeDtoWithId> updateTransactionType(
            @PathVariable("id") Long id,
            @Valid @RequestBody TransactionTypeDtoWithId transactionTypeDtoWithId) {
        if (!id.equals(transactionTypeDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            TransactionTypeDtoWithId updatedDto = transactionTypeService.update(transactionTypeDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionType(@PathVariable ("id") Long id) {
        try {
            transactionTypeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}