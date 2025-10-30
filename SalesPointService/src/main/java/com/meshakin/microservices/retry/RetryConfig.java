package com.meshakin.microservices.retry;

import com.meshakin.microservices.exception.RetryableRemoteServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

    @Value("${retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${retry.initialDelay:1000}")
    private long initialDelay;

    @Value("${retry.multiplier:2}")
    private double multiplier;

    @Bean
    public RetryTemplate retryTemplate() {

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);

        ExponentialRandomBackOffPolicy backOffPolicy = new ExponentialRandomBackOffPolicy();
        backOffPolicy.setInitialInterval(initialDelay);
        backOffPolicy.setMultiplier(multiplier);
        backOffPolicy.setMaxInterval(10000);

        return RetryTemplate.builder()
                .customPolicy(retryPolicy)
                .customBackoff(backOffPolicy)
                .retryOn(RetryableRemoteServiceException.class)
                .traversingCauses()
                .build();
    }
}