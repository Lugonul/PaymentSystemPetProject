package com.meshakin.microservice.rabbitmq.output;


import com.meshakin.microservice.rabbitmq.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitResponseSender {
    private final RabbitTemplate rabbitTemplate;

    public void sendTransactionStatus(ResponseDto responseDto) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.RESPONSE_ROUTING_KEY,
                responseDto);
    }
}
