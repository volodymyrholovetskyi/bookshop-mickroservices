--liquibase formatted sql
--changeset vholovetskyi:4

CREATE TABLE IF NOT EXISTS orders
(
    order_id    SERIAL        NOT NULL,
    status      VARCHAR(70) NOT NULL,
    cust_id     INT8        NOT NULL,
    order_date  DATE DEFAULT NOW(),
    gross_value decimal(6, 2) NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT fk_customer_order_id FOREIGN KEY (cust_id)
    REFERENCES customer (cust_id)
    ON DELETE CASCADE
);
