CREATE TABLE fund_details(
    id INTEGER NOT NULL PRIMARY KEY,
    ledger_id INTEGER NOT NULL,
    last_yield_amount DECIMAL,
    dividend_yield DECIMAL,
    stock_price DECIMAL,
    quantity INTEGER,
    pvp DECIMAL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_fund_details
START WITH 1
INCREMENT BY 1;