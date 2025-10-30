package com.meshakin.microservice.rabbitmq.input;

import com.meshakin.microservice.rabbitmq.RabbitConfig;
import com.meshakin.microservice.rabbitmq.output.RabbitResponseSender;
import com.meshakin.microservice.rabbitmq.output.ResponseDto;
import com.meshakin.rest.dto.with.id.AccountDtoWithId;
import com.meshakin.rest.service.AccountService;
import com.rabbitmq.client.LongString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventHandler {

    private final AccountService accountService;
    private final RabbitResponseSender rabbitResponseSender;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public void handlePaymentEvent(RequestDto requestDto) {


        log.info("Received request: {}", requestDto );

        Long responseCodeId = 2L;
        Long transactionId = requestDto.id();

        AccountDtoWithId account = accountService.read(1L).orElse(null);
        if (account != null) {
            if (Math.random() > 0.5) {
                accountService.update(new AccountDtoWithId(
                        account.id(),
                        account.accountNumber(),
                        account.balance().subtract(requestDto.sum()),
                        account.currencyId(),
                        account.accountTypeId(),
                        account.clientId(),
                        account.accountOpeningDate(),
                        account.suspendingOperation(),
                        account.accountClosedDate(),
                        account.version()
                ));
            } else {
                accountService.update(new AccountDtoWithId(
                        account.id(),
                        account.accountNumber(),
                        account.balance().add(requestDto.sum()),
                        account.currencyId(),
                        account.accountTypeId(),
                        account.clientId(),
                        account.accountOpeningDate(),
                        account.suspendingOperation(),
                        account.accountClosedDate(),
                        account.version()
                ));
            }
            rabbitResponseSender.sendTransactionStatus(new ResponseDto(transactionId, responseCodeId));
        }
    }

    private String extractTokenFromMessage(Message message) {
        Object tokenHeader = message.getMessageProperties().getHeaders().get("X-Auth-Token");

        if (tokenHeader instanceof String) {
            return (String) tokenHeader;
        } else if (tokenHeader instanceof LongString) {
            return ((LongString) tokenHeader).toString();
        } else if (tokenHeader instanceof byte[]) {
            return new String((byte[]) tokenHeader, StandardCharsets.UTF_8);
        }

        throw new IllegalArgumentException("Invalid token format");
    }

}