package com.meshakin.rest.controller;

import com.meshakin.rest.dto.SalesPointDtoWithId;
import com.meshakin.rest.dto.SalesPointDtoWithoutId;
import com.meshakin.rest.service.SalesPointService;
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
@RequestMapping("/api/salesPoints")
@RequiredArgsConstructor
public class SalesPointController {

    private final SalesPointService salesPointService;

    @GetMapping
    public ResponseEntity<List<SalesPointDtoWithId>> getAllSalesPoints() {
        List<SalesPointDtoWithId> salesPoints = salesPointService.readAll();
        return ResponseEntity.ok(salesPoints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesPointDtoWithId> getSalesPointById(@PathVariable("id") Long id) {
        return salesPointService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SalesPointDtoWithId> createSalesPoint(
            @Valid @RequestBody SalesPointDtoWithoutId salesPointDtoWithoutId) {
        SalesPointDtoWithId salesPointDtoWithId = salesPointService.create(salesPointDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salesPointDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(salesPointDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesPointDtoWithId> updateSalesPoint(
            @PathVariable("id") Long id,
            @Valid @RequestBody SalesPointDtoWithId salesPointDtoWithId) {
        if (!id.equals(salesPointDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            SalesPointDtoWithId updatedDto = salesPointService.update(salesPointDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesPoint(@PathVariable ("id") Long id) {
        try {
            salesPointService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}