CREATE TABLE fixed_income_funds(
    spending_id INTEGER NOT NULL PRIMARY KEY,
    due_date DATE NOT NULL,
    annual_profit_percentage DECIMAL NOT NULL,
    product_price DECIMAL NOT NULL,
    minAmount_allowed DECIMAL NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_fixed_income_funds
START WITH 1
INCREMENT BY 1;