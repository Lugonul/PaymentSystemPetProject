package com.meshakin.rest.controller;

import com.meshakin.rest.dto.TerminalDtoWithId;
import com.meshakin.rest.dto.TerminalDtoWithoutId;
import com.meshakin.rest.service.TerminalService;
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
@RequestMapping("/api/terminals")
@RequiredArgsConstructor
public class TerminalController {

    private final TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<TerminalDtoWithId>> getAllTerminals() {
        List<TerminalDtoWithId> terminals = terminalService.readAll();
        return ResponseEntity.ok(terminals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminalDtoWithId> getTerminalById(@PathVariable("id") Long id) {
        return terminalService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TerminalDtoWithId> createTerminal(
            @Valid @RequestBody TerminalDtoWithoutId terminalDtoWithoutId) {
        TerminalDtoWithId terminalDtoWithId = terminalService.create(terminalDtoWithoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(terminalDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(terminalDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalDtoWithId> updateTerminal(
            @PathVariable("id") Long id,
            @Valid @RequestBody TerminalDtoWithId terminalDtoWithId) {
        if (!id.equals(terminalDtoWithId.id())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            TerminalDtoWithId updatedDto = terminalService.update(terminalDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminal(@PathVariable ("id") Long id) {
        try {
            terminalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}