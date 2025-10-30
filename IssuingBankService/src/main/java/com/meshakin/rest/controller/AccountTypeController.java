package com.meshakin.rest.controller;

import com.meshakin.rest.dto.with.id.AccountTypeDtoWithId;
import com.meshakin.rest.dto.without.id.AccountTypeDtoWithoutId;
import com.meshakin.rest.service.AccountTypeService;
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
@RequestMapping("admin/accountTypes")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<List<AccountTypeDtoWithId>> getAllAccountTypes() {
        return ResponseEntity.ok(accountTypeService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeDtoWithId> getAccountTypeById(
            @PathVariable("id") Long id) {
        return accountTypeService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountTypeDtoWithId> saveAccountType(@Valid @RequestBody AccountTypeDtoWithoutId accountTypeDtoWithoutId) {
        AccountTypeDtoWithId accountTypeDtoWithId = accountTypeService.create(accountTypeDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accountTypeDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(accountTypeDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountTypeDtoWithId> updateAccountType(@Valid @RequestBody AccountTypeDtoWithId accountTypeDtoWithId) {
        try {
            AccountTypeDtoWithId updatedDto = accountTypeService.update(accountTypeDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountType(@PathVariable("id") Long id){
        try {
            accountTypeService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}