package com.meshakin.microservices.feign.output;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payments/request")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessingCenterClient processingCenterClient;
    private final RetryTemplate retryTemplate;

    @PostMapping
    public ResponseEntity<String> startTransaction(@RequestBody @Valid RequestDTO requestDTO) {

        retryTemplate.execute(context -> {
            processingCenterClient.startTransaction(requestDTO);
            return null;
        });

        return ResponseEntity.ok("Process started");
    }
}