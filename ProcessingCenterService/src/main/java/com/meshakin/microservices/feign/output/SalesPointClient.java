package com.meshakin.microservices.feign.output;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "sales-point-service")
public interface SalesPointClient {
    @PostMapping("/api/payments/response")
    String simpleTest();
}