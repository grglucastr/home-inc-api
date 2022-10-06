CREATE TABLE fixed_income_funds(
    id INTEGER NOT NULL PRIMARY KEY,
    spending_id INTEGER NOT NULL,
    dueDate DATE NOT NULL,
    annualProfitPercentage DECIMAL NOT NULL,
    productPrice DECIMAL NOT NULL,
    minAmountAllowed DECIMAL NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY (spending_id) REFERENCES spendings(id)
);

CREATE SEQUENCE se_fixed_income_funds
START WITH 1
INCREMENT BY 1;