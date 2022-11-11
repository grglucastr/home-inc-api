CREATE TABLE spendings(
    id INTEGER NOT NULL PRIMARY KEY,
    spending_category_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    installments INTEGER,
    periodicity VARCHAR(15) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_spendings
START WITH 1
INCREMENT BY 1;