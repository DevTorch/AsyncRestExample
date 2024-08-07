-- liquibase formatted sql

-- changeset Torchez:1723005480898-1
CREATE TABLE customer (id BIGINT NOT NULL, full_name VARCHAR(255), email VARCHAR(255), gender VARCHAR(255), created TIMESTAMP WITHOUT TIME ZONE, last_updated TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT pk_customer PRIMARY KEY (id));

-- changeset Torchez:1723005480898-2
ALTER TABLE customer ADD CONSTRAINT uc_customer_email UNIQUE (email);

