CREATE TABLE incomes(
    id INTEGER NOT NULL PRIMARY KEY,
    income_category_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    name VARCHAR(80) NOT NULL,
    amount DECIMAL NOT NULL,
    periodicity VARCHAR(15) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY (income_category_id) REFERENCES income_categories(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE SEQUENCE se_incomes
START WITH 1
INCREMENT BY 1;