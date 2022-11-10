CREATE TABLE fixed_income_funds(
    spending_id INTEGER NOT NULL PRIMARY KEY,
    dueDate DATE NOT NULL,
    annualProfitPercentage DECIMAL NOT NULL,
    productPrice DECIMAL NOT NULL,
    minAmountAllowed DECIMAL NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP
);

CREATE SEQUENCE se_fixed_income_funds
START WITH 1
INCREMENT BY 1;