package com.meshakin.rest.controller;

import com.meshakin.rest.dto.with.id.ClientDtoWithId;
import com.meshakin.rest.dto.without.id.ClientDtoWithoutId;
import com.meshakin.rest.service.ClientService;
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
@RequestMapping("admin/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDtoWithId>> getAllClients() {
        return ResponseEntity.ok(clientService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDtoWithId> getClientById(
            @PathVariable("id") Long id) {
        return clientService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientDtoWithId> saveClient(@Valid @RequestBody ClientDtoWithoutId clientDtoWithoutId) {
        ClientDtoWithId clientDtoWithId = clientService.create(clientDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(clientDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDtoWithId> updateClient(@Valid @RequestBody ClientDtoWithId clientDtoWithId) {
        try {
            ClientDtoWithId updatedDto = clientService.update(clientDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id){
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}