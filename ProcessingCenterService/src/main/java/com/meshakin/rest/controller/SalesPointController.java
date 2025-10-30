package com.meshakin.rest.controller;

import com.meshakin.rest.dto.id.SalesPointDtoWithId;
import com.meshakin.rest.dto.no.id.SalesPointDtoWithoutId;
import com.meshakin.rest.service.SalesPointService;
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
@RequestMapping("api/salesPoints")
public class SalesPointController {

    private final SalesPointService salesPointService;

    @GetMapping
    public ResponseEntity<List<SalesPointDtoWithId>> getAllSalesPoints() {
        return ResponseEntity.ok(salesPointService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPointDtoWithId> getSalesPointById(
            @PathVariable("id") Long id) {
        return salesPointService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalesPointDtoWithId> saveSalesPoint(@Valid @RequestBody SalesPointDtoWithoutId salesPointDtoWithoutId) {
        SalesPointDtoWithId salesPointDtoWithId = salesPointService.create(salesPointDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salesPointDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(salesPointDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesPointDtoWithId> updateSalesPoint(@Valid @RequestBody SalesPointDtoWithId salesPointDtoWithId) {
        try {
            SalesPointDtoWithId updatedDto = salesPointService.update(salesPointDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPoint(@PathVariable("id") Long id) {
        try {
            salesPointService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
