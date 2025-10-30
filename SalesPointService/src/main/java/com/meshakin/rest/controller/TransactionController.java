package com.meshakin.rest.controller;

import com.meshakin.rest.dto.TransactionDtoWithId;
import com.meshakin.rest.dto.TransactionDtoWithoutId;
import com.meshakin.rest.service.TransactionService;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDtoWithId>> getAllTransactions() {
        List<TransactionDtoWithId> transactions = transactionService.readAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDtoWithId> getTransactionById(@PathVariable("id") Long id) {
        return transactionService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionDtoWithId> createTransaction(
            @Valid @RequestBody TransactionDtoWithoutId transactionDtoWithoutId) {
        TransactionDtoWithId transactionDtoWithId = transactionService.create(transactionDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(transactionDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDtoWithId> updateTransaction(
            @PathVariable("id") Long id,
            @Valid @RequestBody TransactionDtoWithId transactionDtoWithId) {
        if (!id.equals(transactionDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            TransactionDtoWithId updatedDto = transactionService.update(transactionDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable ("id") Long id) {
        try {
            transactionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}