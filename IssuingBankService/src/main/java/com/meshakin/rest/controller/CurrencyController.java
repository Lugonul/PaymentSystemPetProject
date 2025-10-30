package com.meshakin.rest.controller;

import com.meshakin.rest.dto.with.id.CurrencyDtoWithId;
import com.meshakin.rest.dto.without.id.CurrencyDtoWithoutId;
import com.meshakin.rest.service.CurrencyService;
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
@RequestMapping("admin/currencys")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDtoWithId>> getAllCurrencys() {
        return ResponseEntity.ok(currencyService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDtoWithId> getCurrencyById(
            @PathVariable("id") Long id) {
        return currencyService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CurrencyDtoWithId> saveCurrency(@Valid @RequestBody CurrencyDtoWithoutId currencyDtoWithoutId) {
        CurrencyDtoWithId currencyDtoWithId = currencyService.create(currencyDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(currencyDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(currencyDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDtoWithId> updateCurrency(@Valid @RequestBody CurrencyDtoWithId currencyDtoWithId) {
        try {
            CurrencyDtoWithId updatedDto = currencyService.update(currencyDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable("id") Long id){
        try {
            currencyService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
