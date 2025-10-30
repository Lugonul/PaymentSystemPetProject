package com.meshakin.microservices.feign;

import com.meshakin.microservices.exception.ClientErrorException;
import com.meshakin.microservices.exception.EmptyResponseException;
import com.meshakin.microservices.exception.RemoteServiceException;
import com.meshakin.microservices.exception.ServerErrorException;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            int status = response.status();

            if (status >= 500) {
                return new ServerErrorException("Server erсщьror: " + status);
            } else if (status == 200 && response.body().length() == 0) {
                return new EmptyResponseException("Empty response body");
            } else if (status >= 400 && status < 500) {
                return new ClientErrorException("Client error: " + status);
            } else {
                return new RemoteServiceException("Unexpected error: " + status);
            }
        };
    }

@Bean
public RequestInterceptor feignTokenInterceptor() {
    return requestTemplate -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getCredentials();
            requestTemplate.header("Authorization", "Bearer " + jwt.getTokenValue());
        }
    };
}
}
