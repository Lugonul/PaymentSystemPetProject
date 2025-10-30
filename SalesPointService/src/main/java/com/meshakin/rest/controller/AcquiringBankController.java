package com.meshakin.rest.controller;

import com.meshakin.rest.dto.AcquiringBankDtoWithId;
import com.meshakin.rest.dto.AcquiringBankDtoWithoutId;
import com.meshakin.rest.service.AcquiringBankService;
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
@RequestMapping("/api/acquiringBanks")
@RequiredArgsConstructor
public class AcquiringBankController {

    private final AcquiringBankService acquiringBankService;

    @GetMapping
    public ResponseEntity<List<AcquiringBankDtoWithId>> getAllAcquiringBanks() {
        List<AcquiringBankDtoWithId> acquiringBanks = acquiringBankService.readAll();
        return ResponseEntity.ok(acquiringBanks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquiringBankDtoWithId> getAcquiringBankById(@PathVariable("id") Long id) {
        return acquiringBankService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AcquiringBankDtoWithId> createAcquiringBank(
            @Valid @RequestBody AcquiringBankDtoWithoutId acquiringBankDtoWithoutId) {
        AcquiringBankDtoWithId acquiringBankDtoWithId = acquiringBankService.create(acquiringBankDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(acquiringBankDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(acquiringBankDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcquiringBankDtoWithId> updateAcquiringBank(
            @PathVariable("id") Long id,
            @Valid @RequestBody AcquiringBankDtoWithId acquiringBankDtoWithId) {
        if (!id.equals(acquiringBankDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            AcquiringBankDtoWithId updatedDto = acquiringBankService.update(acquiringBankDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcquiringBank(@PathVariable ("id") Long id) {
        try {
            acquiringBankService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}