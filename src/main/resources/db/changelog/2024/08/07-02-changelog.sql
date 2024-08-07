-- liquibase formatted sql

-- changeset Torchez:1723049764029-1
CREATE SEQUENCE  IF NOT EXISTS customer_id_seq;
ALTER TABLE customer ALTER COLUMN  id SET NOT NULL;
ALTER TABLE customer ALTER COLUMN  id SET DEFAULT nextval('customer_id_seq');
ALTER SEQUENCE customer_id_seq OWNED BY customer.id;

