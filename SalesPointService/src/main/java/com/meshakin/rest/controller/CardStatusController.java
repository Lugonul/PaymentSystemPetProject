package com.meshakin.rest.controller;

import com.meshakin.rest.dto.CardStatusDtoWithId;
import com.meshakin.rest.dto.CardStatusDtoWithoutId;
import com.meshakin.rest.service.CardStatusService;
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
@RequestMapping("/api/cardStatuses")
@RequiredArgsConstructor
public class CardStatusController {

    private final CardStatusService cardStatusService;

    @GetMapping
    public ResponseEntity<List<CardStatusDtoWithId>> getAllCardStatuss() {
        List<CardStatusDtoWithId> cardStatuss = cardStatusService.readAll();
        return ResponseEntity.ok(cardStatuss);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardStatusDtoWithId> getCardStatusById(@PathVariable("id") Long id) {
        return cardStatusService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardStatusDtoWithId> createCardStatus(
            @Valid @RequestBody CardStatusDtoWithoutId cardStatusDtoWithoutId) {
        CardStatusDtoWithId cardStatusDtoWithId = cardStatusService.create(cardStatusDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardStatusDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(cardStatusDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardStatusDtoWithId> updateCardStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody CardStatusDtoWithId cardStatusDtoWithId) {
        if (!id.equals(cardStatusDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CardStatusDtoWithId updatedDto = cardStatusService.update(cardStatusDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardStatus(@PathVariable ("id") Long id) {
        try {
            cardStatusService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}