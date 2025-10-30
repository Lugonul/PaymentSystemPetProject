package com.meshakin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SalesPointServiceRunner {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SalesPointServiceRunner.class, args);

        System.out.println("Приложение запущено на http://localhost:8082");
        System.out.println("Проверьте работоспособность Swagger UI: http://localhost:8082/swagger-ui/index.html");
        System.out.println("Json документация: http://localhost:8082/v3/api-docs");
    }
}