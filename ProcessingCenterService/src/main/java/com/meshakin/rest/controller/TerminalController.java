package com.meshakin.rest.controller;

import com.meshakin.rest.dto.id.TerminalDtoWithId;
import com.meshakin.rest.dto.no.id.TerminalDtoWithoutId;
import com.meshakin.rest.service.TerminalService;
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
@RequestMapping("api/terminals")
public class TerminalController {

    private final TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<TerminalDtoWithId>> getAllTerminals() {
        return ResponseEntity.ok(terminalService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalDtoWithId> getTerminalById(
            @PathVariable("id") Long id) {
        return terminalService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TerminalDtoWithId> saveTerminal(@Valid @RequestBody TerminalDtoWithoutId terminalDtoWithoutId) {
        TerminalDtoWithId terminalDtoWithId = terminalService.create(terminalDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(terminalDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(terminalDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalDtoWithId> updateTerminal(@Valid @RequestBody TerminalDtoWithId terminalDtoWithId) {
        try {
            TerminalDtoWithId updatedDto = terminalService.update(terminalDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminal(@PathVariable("id") Long id) {
        try {
            terminalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
