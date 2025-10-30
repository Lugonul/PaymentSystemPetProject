package com.meshakin.rest.controller;

import com.meshakin.rest.dto.id.ResponseCodeDtoWithId;
import com.meshakin.rest.dto.no.id.ResponseCodeDtoWithoutId;
import com.meshakin.rest.service.ResponseCodeService;
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
@RequestMapping("api/responseCodes")
public class ResponseCodeController {

    private final ResponseCodeService responseCodeService;

    @GetMapping
    public ResponseEntity<List<ResponseCodeDtoWithId>> getAllResponseCodes() {
        return ResponseEntity.ok(responseCodeService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCodeDtoWithId> getResponseCodeById(
            @PathVariable("id") Long id) {
        return responseCodeService.read(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResponseCodeDtoWithId> saveResponseCode(@Valid @RequestBody ResponseCodeDtoWithoutId responseCodeDtoWithoutId) {
        ResponseCodeDtoWithId responseCodeDtoWithId = responseCodeService.create(responseCodeDtoWithoutId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseCodeDtoWithId.id())
                .toUri();
        return ResponseEntity.created(location).body(responseCodeDtoWithId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCodeDtoWithId> updateResponseCode(@Valid @RequestBody ResponseCodeDtoWithId responseCodeDtoWithId) {
        try {
            ResponseCodeDtoWithId updatedDto = responseCodeService.update(responseCodeDtoWithId);
            return ResponseEntity.ok(updatedDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponseCode(@PathVariable("id") Long id) {
        try {
            responseCodeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
