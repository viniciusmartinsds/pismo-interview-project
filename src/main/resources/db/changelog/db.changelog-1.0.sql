--liquibase formatted sql

--changeset viniciusmartins:1
CREATE TABLE IF NOT EXISTS accounts (
    account_id serial PRIMARY KEY,
    document_number VARCHAR (255) UNIQUE NOT NULL
)
--rollback drop table Accounts;