package com.meshakin.microservices.rabbitmq.input;

import com.meshakin.microservices.rabbitmq.RabbitConfig;
import com.meshakin.rest.dto.id.TransactionDtoWithId;
import com.meshakin.rest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseListener {

    private final TransactionService transactionService;

    @RabbitListener(queues = RabbitConfig.RESPONSE_QUEUE)
    public void receiveMessage(ResponseDto responseDto) {

        if (responseDto.responseCodeId() != null && responseDto.transactionId() != null) {
            TransactionDtoWithId transactionDtoWithId = transactionService.read(responseDto.transactionId()).orElse(
                    transactionService.read(1L).orElse(null)
            );

            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);
            System.out.println(transactionDtoWithId);


            transactionService.update(new TransactionDtoWithId(
                    transactionDtoWithId.id(),
                    transactionDtoWithId.transactionDate(),
                    transactionDtoWithId.sum(),
                    transactionDtoWithId.transactionTypeId(),
                    transactionDtoWithId.cardId(),
                    transactionDtoWithId.terminalId(),
                    responseDto.responseCodeId(),
                    transactionDtoWithId.authorizationCode(),
                    transactionDtoWithId.version()
            ));
        }
    }
}

