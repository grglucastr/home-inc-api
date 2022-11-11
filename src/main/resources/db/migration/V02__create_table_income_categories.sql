CREATE TABLE income_categories(
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    user_id INTEGER NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_income_category
START WITH 3
INCREMENT BY 1;