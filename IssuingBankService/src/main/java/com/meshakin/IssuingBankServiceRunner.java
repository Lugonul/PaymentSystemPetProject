package com.meshakin;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class IssuingBankServiceRunner {

	public static void main(String[] args) {


		SpringApplication.run(IssuingBankServiceRunner.class, args);

		System.out.println("Приложение запущено на http://localhost:8083");
		System.out.println("Проверьте работоспособность Swagger UI: http://localhost:8083/swagger-ui/index.html");
		System.out.println("Json документация: http://localhost:8083/v3/api-docs");
	}
}