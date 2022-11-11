CREATE TABLE incomes(
    id INTEGER NOT NULL PRIMARY KEY,
    income_category_id INTEGER NOT NULL,
    name VARCHAR(80) NOT NULL,
    amount DECIMAL NOT NULL,
    periodicity VARCHAR(15) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_incomes
START WITH 1
INCREMENT BY 1;