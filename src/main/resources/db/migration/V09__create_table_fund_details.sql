CREATE TABLE fund_details(
    id INTEGER NOT NULL PRIMARY KEY,
    ledger_id INTEGER NOT NULL,
    lastYieldAmount DECIMAL,
    dividendYield DECIMAL,
    stockPrice DECIMAL,
    quantity INTEGER,
    pvp DECIMAL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY (ledger_id) REFERENCES ledgers(id)
);

CREATE SEQUENCE se_fund_details
START WITH 1
INCREMENT BY 1;