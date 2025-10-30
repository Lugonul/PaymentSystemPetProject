package com.meshakin.rest.controller;

import com.meshakin.rest.dto.MerchantCategoryCodeDtoWithId;
import com.meshakin.rest.dto.MerchantCategoryCodeDtoWithoutId;
import com.meshakin.rest.service.MerchantCategoryCodeService;
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
@RequestMapping("/api/merchantCategoryCodes")
@RequiredArgsConstructor
public class MerchantCategoryCodeController {

    private final MerchantCategoryCodeService merchantCategoryCodeService;

    @GetMapping
    public ResponseEntity<List<MerchantCategoryCodeDtoWithId>> getAllMerchantCategoryCodes() {
        List<MerchantCategoryCodeDtoWithId> merchantCategoryCodes = merchantCategoryCodeService.readAll();
        return ResponseEntity.ok(merchantCategoryCodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantCategoryCodeDtoWithId> getMerchantCategoryCodeById(@PathVariable("id") Long id) {
        return merchantCategoryCodeService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MerchantCategoryCodeDtoWithId> createMerchantCategoryCode(
            @Valid @RequestBody MerchantCategoryCodeDtoWithoutId merchantCategoryCodeDtoWithoutId) {
        MerchantCategoryCodeDtoWithId merchantCategoryCodeDtoWithId = merchantCategoryCodeService.create(merchantCategoryCodeDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(merchantCategoryCodeDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(merchantCategoryCodeDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantCategoryCodeDtoWithId> updateMerchantCategoryCode(
            @PathVariable("id") Long id,
            @Valid @RequestBody MerchantCategoryCodeDtoWithId merchantCategoryCodeDtoWithId) {
        if (!id.equals(merchantCategoryCodeDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            MerchantCategoryCodeDtoWithId updatedDto = merchantCategoryCodeService.update(merchantCategoryCodeDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchantCategoryCode(@PathVariable ("id") Long id) {
        try {
            merchantCategoryCodeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}