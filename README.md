# Payment System Pet Project

Микросервисная система обработки платежей.

Данный проект был написан около 3 месяцев назад, когда я имел крайне смутное представление о совместной разработке, поэтому имеет большое количество недостатков, в изначальной версии он даже не запускался без предварительной настройки. Сейчас проект запускается, но нужно в ручную чуть настроить keycloak по инструкции в директории keycloak. По большей части была проделана работа по автоматизации запуска, множество недостатков все еще актуальны.

## Технологии
- Java 21, Spring Boot 3, Spring Cloud
- PostgreSQL, Redis, RabbitMQ
- Keycloak (аутентификация)
- Docker, Docker Compose
- Eureka Server, Config Server

## Быстрый старт

Для старта достаточно клонировать репозиторий, запустить docker-compose и настроить keycloak по инструкции в директории keycloak


## Сервисы и порты
Сервис  Порт	Описание	URL

Keycloak	8180	Аутентификация	http://localhost:8180

Eureka Server	8001	Service Discovery	http://localhost:8001

Config Server	8888	Конфигурация	http://localhost:8888

Processing Center	8081	Обработка платежей	http://localhost:8081

Sales Point	8082	Торговые точки	http://localhost:8082

Issuing Bank	8083	Банк-эмитент	http://localhost:8083

PostgreSQL	5432	База данных	http://localhost:5432

Redis	6379	Кэш	http://localhost:6379

RabbitMQ	15672	Очередь сообщений	http://localhost:15672

Jaeger	16686	Трейсинг	http://localhost:16686

## Базовые пароли:

PostgreSQL: mydb admin/admin

RabbitMQ: guest/guest

Keycloak Admin: admin/admin
