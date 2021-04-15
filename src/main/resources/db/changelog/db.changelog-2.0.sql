--liquibase formatted sql

--changeset viniciusmartins:2
CREATE TABLE IF NOT EXISTS operation_types (
    operation_type_id SERIAL PRIMARY KEY,
    description VARCHAR (50) UNIQUE NOT NULL
);
--rollback drop table operationtypes;

--changeset viniciusmartins:3
INSERT INTO operation_types values (1, 'COMPRA_A_VISTA');
INSERT INTO operation_types values (2, 'COMPRA_PARCELADA');
INSERT INTO operation_types values (3, 'SAQUE');
INSERT INTO operation_types values (4, 'PAGAMENTO');
--rollback delete from table operationtypes where operationtype_id in (1, 2, 3, 4);

--changeset viniciusmartins:4
CREATE TABLE IF NOT EXISTS transactions(
    transaction_id SERIAL PRIMARY KEY,
    account_id SERIAL REFERENCES accounts(account_id),
    operation_type_id SERIAL REFERENCES operation_types(operation_type_id),
    amount DECIMAL,
    event_date TIMESTAMP
);