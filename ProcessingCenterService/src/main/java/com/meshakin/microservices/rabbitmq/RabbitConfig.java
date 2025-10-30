package com.meshakin.microservices.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitConfig {
    public static final String REQUEST_QUEUE = "pc.to.ib.queue";
    public static final String RESPONSE_QUEUE = "ib.to.pc.queue";
    public static final String EXCHANGE = "bank.exchange";
    public static final String REQUEST_ROUTING_KEY = "pc.ib";
    public static final String RESPONSE_ROUTING_KEY = "ib.pc";


    // DLQ для обработки ошибок
    private static final String REQUEST_DLQ = REQUEST_QUEUE + ".dlq";
    private static final String RESPONSE_DLQ = RESPONSE_QUEUE + ".dlq";
    private static final String DLX_EXCHANGE = EXCHANGE + ".dlx";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE);
    }

    @Bean
    public Binding requestBinding() {
        return BindingBuilder.bind(requestQueue())
                .to(exchange())
                .with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding responseBinding() {
        return BindingBuilder.bind(responseQueue())
                .to(exchange())
                .with(RESPONSE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

// БИНЫ ДЛЯ ОБРАБОТКИ ОШИБОК

    // Dead Letter Exchange
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // DLQ для запросов
    @Bean
    public Queue requestDlq() {
        return new Queue(REQUEST_DLQ, true);  // durable = true
    }

    // DLQ для ответов
    @Bean
    public Queue responseDlq() {
        return new Queue(RESPONSE_DLQ, true);
    }

    // Привязки DLQ
    @Bean
    public Binding requestDlqBinding() {
        return BindingBuilder.bind(requestDlq())
                .to(dlxExchange())
                .with(REQUEST_DLQ);
    }

    @Bean
    public Binding responseDlqBinding() {
        return BindingBuilder.bind(responseDlq())
                .to(dlxExchange())
                .with(RESPONSE_DLQ);
    }

    // КОНФИГУРАЦИЯ ПОВТОРНЫХ ПОПЫТОК
    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .backOffOptions(2000, 2.0, 5000) // Стартовая задержка 2с, множитель 2, макс. задержка 5с
                .recoverer(new RejectAndDontRequeueRecoverer()) // После всех попыток - в DLQ
                .build();
    }

    // ФАБРИКА ДЛЯ КОНТЕЙНЕРОВ СЛУШАТЕЛЕЙ
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());

        // Настройка повторов
        factory.setAdviceChain(retryInterceptor());

        // Отключаем автоматическую requeue при ошибках
        factory.setDefaultRequeueRejected(false);

        // Параллелизм обработки
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);

        return factory;
    }
}