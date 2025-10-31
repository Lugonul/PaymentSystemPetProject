package com.meshakin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableRetry
public class ProcessingCenterServiceRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProcessingCenterServiceRunner.class);


    public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(ProcessingCenterServiceRunner.class, args);
        System.out.println("Приложение запущено на http://localhost:8081");
        System.out.println("Проверьте работоспособность Swagger UI: http://localhost:8081/swagger-ui/index.html");
        System.out.println("Json документация: http://localhost:8081/custom-api-docs");
    }
}