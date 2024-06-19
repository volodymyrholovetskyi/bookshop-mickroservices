--liquibase formatted sql
--changeset vholovetskyi:1

CREATE TABLE IF NOT EXISTS customer
(
    cust_id SERIAL      NOT NULL,
    first_name  VARCHAR(70) NOT NULL,
    last_name   VARCHAR(70) NOT NULL,
    email       VARCHAR(70) NOT NULL UNIQUE,
    phone       VARCHAR(16) NOT NULL,
    street      VARCHAR(80) NOT NULL,
    city        VARCHAR(70) NOT NULL,
    zip_code    VARCHAR(10) NOT NULL,
    PRIMARY KEY (cust_id)
);
