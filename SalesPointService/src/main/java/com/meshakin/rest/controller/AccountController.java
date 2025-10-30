package com.meshakin.rest.controller;

import com.meshakin.rest.dto.AccountDtoWithId;
import com.meshakin.rest.dto.AccountDtoWithoutId;
import com.meshakin.rest.service.AccountService;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDtoWithId>> getAllAccounts() {
        List<AccountDtoWithId> accounts = accountService.readAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoWithId> getAccountById(@PathVariable("id") Long id) {
        return accountService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountDtoWithId> createAccount(
            @Valid @RequestBody AccountDtoWithoutId accountDtoWithoutId) {
        AccountDtoWithId accountDtoWithId = accountService.create(accountDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(accountDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDtoWithId> updateAccount(
            @PathVariable("id") Long id,
            @Valid @RequestBody AccountDtoWithId accountDtoWithId) {
        if (!id.equals(accountDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            AccountDtoWithId updatedDto = accountService.update(accountDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable ("id") Long id) {
        try {
            accountService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}