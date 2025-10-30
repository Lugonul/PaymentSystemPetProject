package com.meshakin.rest.controller;

import com.meshakin.rest.dto.CurrencyDtoWithId;
import com.meshakin.rest.dto.CurrencyDtoWithoutId;
import com.meshakin.rest.service.CurrencyService;
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
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDtoWithId>> getAllCurrencys() {
        List<CurrencyDtoWithId> currencys = currencyService.readAll();
        return ResponseEntity.ok(currencys);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDtoWithId> getCurrencyById(@PathVariable("id") Long id) {
        return currencyService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CurrencyDtoWithId> createCurrency(
            @Valid @RequestBody CurrencyDtoWithoutId currencyDtoWithoutId) {
        CurrencyDtoWithId currencyDtoWithId = currencyService.create(currencyDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(currencyDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(currencyDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDtoWithId> updateCurrency(
            @PathVariable("id") Long id,
            @Valid @RequestBody CurrencyDtoWithId currencyDtoWithId) {
        if (!id.equals(currencyDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CurrencyDtoWithId updatedDto = currencyService.update(currencyDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable ("id") Long id) {
        try {
            currencyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}