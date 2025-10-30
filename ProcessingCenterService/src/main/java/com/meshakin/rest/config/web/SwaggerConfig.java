package com.meshakin.rest.config.web;

/**
 * Конфигурационный класс для автоматической документации.
 * <p>
 * Настройки взяты дефолтные из зависимостей.
 * Добавлена возможно аутентификации через swagger-ui
 *
 *
 */


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/api/**", "/admin/**")
                .build();
    }
    @Bean
    public GroupedOpenApi v3ApiDocs() {
        return GroupedOpenApi.builder()
                .group("v3-api-docs")
                .pathsToMatch("/v3/api-docs/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0")
                        .description("API Documentation"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                .components(new Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .name("basicAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                .addServersItem(new Server().url("/").description("Default Server URL"));
    }
}

