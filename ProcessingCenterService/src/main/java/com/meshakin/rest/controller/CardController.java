package com.meshakin.rest.controller;


import com.meshakin.rest.dto.id.CardDtoWithId;
import com.meshakin.rest.dto.no.id.CardDtoWithoutId;
import com.meshakin.rest.service.CardService;
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
@RequestMapping("api/cards")
public class CardController {

    private final CardService cardService;


    @GetMapping
    public List<CardDtoWithId> getAllCards() {
        return cardService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDtoWithId> getCardById(
            @PathVariable("id") Long id) {
        return cardService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardDtoWithId> saveCard(@Valid @RequestBody CardDtoWithoutId cardDtoWithoutId) {
        CardDtoWithId cardDtoWithId = cardService.create(cardDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(cardDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDtoWithId> updateCard(@Valid @RequestBody CardDtoWithId cardDtoWithId) {
        try {
            CardDtoWithId updatedDto = cardService.update(cardDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
        try {
            cardService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
