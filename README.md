# Parrot Challenge
This is an amazing repository that contains the solution to the Parrot Coding Challenge.

The application has the following endpoints available:
- `POST /users`
- `POST /orders`
- `GET /products_report`

[Here](https://api.postman.com/collections/1746446-b3311ef3-8e4a-4282-a8ea-f42ed23162e2?access_key=) you will find a Postman Collection with examples about API usage. Be sure to ask for your Postman API KEY in order to get access to the collection.

## Software used
The following software is required in order to run the application:
- Java SDK 20+
- Gradle 8.2.1
- Docker 24.0.2

## Running the application

The application can be run with the following command:
```console
gradle bootRun
```
The application leverages [Spring Boot 3.1.2 Docker Compose](https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/#features.docker-compose) feature for running a PostgreSQL server. Besides that, it uses [Liquibase](https://www.liquibase.org/) for database migrations management.

## Running tests

The application tests can be run with the following command:
```console
gradle test
```
