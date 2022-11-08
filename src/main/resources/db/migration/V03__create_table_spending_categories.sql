CREATE TABLE spending_categories(
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    user_id INTEGER NOT NULL,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY user_id REFERENCES users(id)
);

CREATE SEQUENCE se_spending_category
START WITH 3
INCREMENT BY 1;