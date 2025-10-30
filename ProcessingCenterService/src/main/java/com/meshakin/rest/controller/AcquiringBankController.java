package com.meshakin.rest.controller;

import com.meshakin.rest.dto.id.AcquiringBankDtoWithId;
import com.meshakin.rest.dto.no.id.AcquiringBankDtoWithoutId;
import com.meshakin.rest.service.AcquiringBankService;
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
@RequestMapping("api/admin/acquiringBanks")
public class AcquiringBankController {

    private final AcquiringBankService acquiringBankService;

    @GetMapping
    public ResponseEntity<List<AcquiringBankDtoWithId>> getAllAcquiringBanks() {
        return ResponseEntity.ok(acquiringBankService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcquiringBankDtoWithId> getAcquiringBankById(
            @PathVariable("id") Long id) {
        return acquiringBankService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AcquiringBankDtoWithId> saveAcquiringBank(@Valid @RequestBody AcquiringBankDtoWithoutId acquiringBankDtoWithoutId) {
        AcquiringBankDtoWithId acquiringBankDtoWithId = acquiringBankService.create(acquiringBankDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(acquiringBankDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(acquiringBankDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcquiringBankDtoWithId> updateAcquiringBank(@Valid @RequestBody AcquiringBankDtoWithId acquiringBankDtoWithId) {
        try {
            AcquiringBankDtoWithId updatedDto = acquiringBankService.update(acquiringBankDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAcquiringBank(@PathVariable("id") Long id){
    try {
        acquiringBankService.delete(id);
        return ResponseEntity.noContent().build();
    }catch (EntityNotFoundException e){
        return ResponseEntity.notFound().build();
    }
    }
}
