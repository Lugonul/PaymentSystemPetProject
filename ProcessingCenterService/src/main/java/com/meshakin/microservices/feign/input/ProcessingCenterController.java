package com.meshakin.microservices.feign.input;

import com.meshakin.microservices.rabbitmq.RabbitConfig;
import com.meshakin.rest.dto.id.TransactionDtoWithId;
import com.meshakin.rest.dto.no.id.TransactionDtoWithoutId;
import com.meshakin.rest.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requestToProcessingCenter")
@RequiredArgsConstructor
@Slf4j
public class ProcessingCenterController {

    private final RabbitTemplate rabbitTemplate;
    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<String> process(@RequestBody TransactionDtoWithoutId requestDto) {

        TransactionDtoWithId requestToIssuingBank = transactionService.create(requestDto);

        log.info("Transaction created, Id: ".concat(requestToIssuingBank.id().toString()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() instanceof Jwt) {
            String token = ((Jwt) authentication.getCredentials()).getTokenValue();


            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE,
                    RabbitConfig.REQUEST_ROUTING_KEY,
                    requestToIssuingBank,
                    message -> {
                        message.getMessageProperties().setHeader("X-Auth-Token", token);
                        return message;
                    });
        }
        return ResponseEntity.ok("Data sent to queue");
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RequestToIssuingBank {
    String token;
    TransactionDtoWithId requestDto;
}