# Notification Service

* [General info](#general-info)
* [Technologies](#technologies)
* [Set-up](#set-up)

## General Info

Service for sending notifications.

## Technologies
- Java 17
- Spring Boot 3
- Spring Boot Data
- Elasticsearch
- RabbitMQ
- Kibana
- Testcontainers
- Lombok
- Maven
- Docker

## Set-up
1. Create .env file with the following properties:
```
SENDER_MAIL_ADDRESS=
SENDER_MAIL_PASSWORD=
RABBITMQ_USERNAME=user
RABBITMQ_PASSWORD=pass123
ELASTIC_USERNAME=elastic
ELASTIC_PASSWORD=pass123
ELASTIC_SECURITY=true
ELASTIC_TOKEN=
ELASTIC_HOST=elasticsearch
ELASTIC_PORT=9200
RABBITMQ_HOST=rabbitmq
RABBITMQ_PORT=5672
NOTIFICATION_SERVICE_PORT=9090
KIBANA_PORT=5601
```
- Add your properties for connecting to the gmail server:
  - `SENDER_MAIL_ADDRESS`
  - `SENDER_MAIL_PASSWORD`
- Properties `ELASTIC_TOKEN` is used to connect to kibana.
- You can generate a `token` in Postman add username and password (Basic Auth) and url (POST method):
```
http://localhost:9200/_security/service/elastic/kibana/credential/token/forkibana
```

2. Run docker compose: 
```
docker-compose up -d
```
