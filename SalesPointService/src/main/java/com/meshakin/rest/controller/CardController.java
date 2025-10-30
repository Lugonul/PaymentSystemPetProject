package com.meshakin.rest.controller;

import com.meshakin.rest.dto.CardDtoWithId;
import com.meshakin.rest.dto.CardDtoWithoutId;
import com.meshakin.rest.service.CardService;
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
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDtoWithId>> getAllCards() {
        List<CardDtoWithId> cards = cardService.readAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDtoWithId> getCardById(@PathVariable("id") Long id) {
        return cardService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardDtoWithId> createCard(
            @Valid @RequestBody CardDtoWithoutId cardDtoWithoutId) {
        CardDtoWithId cardDtoWithId = cardService.create(cardDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(cardDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDtoWithId> updateCard(
            @PathVariable("id") Long id,
            @Valid @RequestBody CardDtoWithId cardDtoWithId) {
        if (!id.equals(cardDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CardDtoWithId updatedDto = cardService.update(cardDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable ("id") Long id) {
        try {
            cardService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}