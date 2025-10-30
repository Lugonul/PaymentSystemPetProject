package com.meshakin.rest.controller;

import com.meshakin.rest.dto.IssuingBankDtoWithId;
import com.meshakin.rest.dto.IssuingBankDtoWithoutId;
import com.meshakin.rest.service.IssuingBankService;
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
@RequestMapping("/api/issuingBanks")
@RequiredArgsConstructor
public class IssuingBankController {

    private final IssuingBankService issuingBankService;

    @GetMapping
    public ResponseEntity<List<IssuingBankDtoWithId>> getAllIssuingBanks() {
        List<IssuingBankDtoWithId> issuingBanks = issuingBankService.readAll();
        return ResponseEntity.ok(issuingBanks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssuingBankDtoWithId> getIssuingBankById(@PathVariable("id") Long id) {
        return issuingBankService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IssuingBankDtoWithId> createIssuingBank(
            @Valid @RequestBody IssuingBankDtoWithoutId issuingBankDtoWithoutId) {
        IssuingBankDtoWithId issuingBankDtoWithId = issuingBankService.create(issuingBankDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(issuingBankDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(issuingBankDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssuingBankDtoWithId> updateIssuingBank(
            @PathVariable("id") Long id,
            @Valid @RequestBody IssuingBankDtoWithId issuingBankDtoWithId) {
        if (!id.equals(issuingBankDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            IssuingBankDtoWithId updatedDto = issuingBankService.update(issuingBankDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssuingBank(@PathVariable ("id") Long id) {
        try {
            issuingBankService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}