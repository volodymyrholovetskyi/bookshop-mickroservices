--liquibase formatted sql
--changeset vholovetskyi:5

CREATE INDEX IF NOT EXISTS index_custId_on_orders ON orders using btree (cust_id);
CREATE INDEX IF NOT EXISTS index_custId_and_status_on_orders ON orders using btree (cust_id, status);
CREATE INDEX IF NOT EXISTS index_custId_and_orderDate_on_orders ON orders using btree (cust_id, order_date);
CREATE INDEX IF NOT EXISTS index_custId_and_status_and_orderDate_on_orders ON orders using btree (cust_id, status, order_date);
