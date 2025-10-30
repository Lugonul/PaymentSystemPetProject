package com.meshakin.rest.controller;

import com.meshakin.rest.dto.id.MerchantCategoryCodeDtoWithId;
import com.meshakin.rest.dto.no.id.MerchantCategoryCodeDtoWithoutId;
import com.meshakin.rest.service.MerchantCategoryCodeService;
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
@RequestMapping("api/merchantCategoryCodes")
public class MerchantCategoryCodeController {

    private final MerchantCategoryCodeService merchantCategoryCodeService;

    @GetMapping
    public ResponseEntity<List<MerchantCategoryCodeDtoWithId>> getAllMerchantCategoryCodes() {
        return ResponseEntity.ok(merchantCategoryCodeService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantCategoryCodeDtoWithId> getMerchantCategoryCodeById(
            @PathVariable("id") Long id) {
        return merchantCategoryCodeService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchantCategoryCodeDtoWithId> saveMerchantCategoryCode(@Valid @RequestBody MerchantCategoryCodeDtoWithoutId merchantCategoryCodeDtoWithoutId) {
        MerchantCategoryCodeDtoWithId merchantCategoryCodeDtoWithId = merchantCategoryCodeService.create(merchantCategoryCodeDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(merchantCategoryCodeDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(merchantCategoryCodeDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantCategoryCodeDtoWithId> updateMerchantCategoryCode(@Valid @RequestBody MerchantCategoryCodeDtoWithId merchantCategoryCodeDtoWithId) {
        try {
            MerchantCategoryCodeDtoWithId updatedDto = merchantCategoryCodeService.update(merchantCategoryCodeDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchantCategoryCode(@PathVariable("id") Long id){
        try {
            merchantCategoryCodeService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}