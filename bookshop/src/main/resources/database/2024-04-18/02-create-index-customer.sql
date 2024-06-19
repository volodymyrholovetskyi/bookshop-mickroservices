--liquibase formatted sql
--changeset vholovetskyi:2

CREATE UNIQUE INDEX IF NOT EXISTS index_email_on_customer ON customer using btree (email);