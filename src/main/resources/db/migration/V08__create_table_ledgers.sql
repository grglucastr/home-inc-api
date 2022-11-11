CREATE TABLE ledgers(
    id INTEGER NOT NULL PRIMARY KEY,
    spending_id INTEGER NOT NULL,
    payment_type_id INTEGER NOT NULL,
    billing_date DATE,
    due_date DATE NOT NULL,
    amount_due DECIMAL NOT NULL,
    barcode TEXT,
    QRCode TEXT,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insert_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date_time TIMESTAMP
);

CREATE SEQUENCE se_ledgers
START WITH 1
INCREMENT BY 1;