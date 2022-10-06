CREATE TABLE spendings(
    id INTEGER NOT NULL PRIMARY KEY,
    spending_category_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    monthsQuantity INTEGER,
    periodicity VARCHAR(15) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY (spending_category_id) REFERENCES spending_categories(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE SEQUENCE se_spendings
START WITH 1
INCREMENT BY 1;