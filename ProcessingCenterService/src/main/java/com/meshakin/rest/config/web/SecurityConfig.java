package com.meshakin.rest.config.web;


/**
 * Конфигурация безопасности приложения.
 * <p>
 * Основные настройки:
 * <p>
 * Аутентификация через БД (таблица user_access)
 * Шифрование паролей BCrypt
 * Настройка правил доступа к URL
 * <p>
 * Правила доступа:
 * <p>
 * /swagger-ui/** = доступ без аутентификации
 * /api/** - доступ без аутентификации
 * /admin/** - только для роли ADMIN
 * Все остальные запросы требуют аутентификации<
 */


import com.meshakin.rest.entity.UserAccess;
import com.meshakin.rest.repository.UserAccessRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "actuator/**"
                        ).permitAll()
                        .requestMatchers("/api/**").authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }


    @Value("${keycloak.jwks-url}")
    private String jwksUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
    }

    @Bean
    @Transactional
    public CommandLineRunner initUsers(UserAccessRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.findByUserLogin("user").isEmpty()) {
                UserAccess user = new UserAccess();
                user.setUserLogin("user");
                user.setUserPassword(encoder.encode("password"));
                user.setFullName("John Doe");
                user.setUserRole("USER");
                repository.save(user);
            }

            if (repository.findByUserLogin("admin").isEmpty()) {
                UserAccess admin = new UserAccess();
                admin.setUserLogin("admin");
                admin.setUserPassword(encoder.encode("password"));
                admin.setFullName("Admin Admin");
                admin.setUserRole("ADMIN");
                repository.save(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
