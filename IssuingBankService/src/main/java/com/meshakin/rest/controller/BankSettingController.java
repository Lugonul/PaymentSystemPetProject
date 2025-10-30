package com.meshakin.rest.controller;

import com.meshakin.rest.dto.with.id.BankSettingDtoWithId;
import com.meshakin.rest.dto.without.id.BankSettingDtoWithoutId;
import com.meshakin.rest.service.BankSettingService;
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
@RequestMapping("admin/bankSettings")
public class BankSettingController {

    private final BankSettingService bankSettingService;

    @GetMapping
    public ResponseEntity<List<BankSettingDtoWithId>> getAllBankSettings() {
        return ResponseEntity.ok(bankSettingService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankSettingDtoWithId> getBankSettingById(
            @PathVariable("id") Long id) {
        return bankSettingService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BankSettingDtoWithId> saveBankSetting(@Valid @RequestBody BankSettingDtoWithoutId bankSettingDtoWithoutId) {
        BankSettingDtoWithId bankSettingDtoWithId = bankSettingService.create(bankSettingDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bankSettingDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(bankSettingDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankSettingDtoWithId> updateBankSetting(@Valid @RequestBody BankSettingDtoWithId bankSettingDtoWithId) {
        try {
            BankSettingDtoWithId updatedDto = bankSettingService.update(bankSettingDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankSetting(@PathVariable("id") Long id){
        try {
            bankSettingService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}