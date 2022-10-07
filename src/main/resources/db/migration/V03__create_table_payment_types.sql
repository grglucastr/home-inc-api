CREATE TABLE payment_types(
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP
);

CREATE SEQUENCE se_payment_types
START WITH 8
INCREMENT BY 1;