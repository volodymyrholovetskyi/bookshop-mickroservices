# Book Shop

* [General info](#general-info)
* [Api-docs](#api-docs)
* [Technologies](#technologies)
* [Set-up](#set-up)

## General Info

Предметна область у проекті описується двома сутностями Order та Customer (many-to-one, unidirectional).

## Api-docs

- Filtering by fields:
  - customerId (required):

    ```
    {
     "customerId": 1
    }
    ```
  - customerId and status(NEW, PAID, CANCELED, SHIPPED):
    ```
    {
     "customerId": 1,
     "status": "NEW"
    }
    ```
  - customerId and orderDate:
    ```
    {
     "customerId": 1,
     "from": "2024-04-15",
     "to": "2024-05-15"
    }
    ```
  - customerId, status and orderDate:
    ```
    {
     "customerId": 1,
     "status": "NEW",
     "from": "2024-04-15",
     "to": "2024-05-15"
    }
    ```

> - Swagger UI: http://localhost:8080/swagger-ui/index.html


## Technologies
- Java 17
- Spring Boot 3
- Spring Boot Data
- Postgresql
- Liquibase
- JUnit 5, Mockito, AssertJ, JUnitParams
- Swagger v.3
- Lombok
- Maven
- Docker

## Set-up
1. Папка [task](./task) містить усі необхідні файли для запуску програми, файл `.bat` запускає попередньо скомпільований jar.
2. База даних автоматично запускається в докер контейнері.
3. Додано Swagger для полегшення тестування endpoints.
