CREATE TABLE ledgers(
    id INTEGER NOT NULL PRIMARY KEY,
    spending_id INTEGER NOT NULL,
    payment_type_id INTEGER NOT NULL,
    billingDate DATE,
    dueDate DATE NOT NULL,
    amountDue DECIMAL NOT NULL,
    barcode TEXT,
    QRCode TEXT,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    insertDateTime TIMESTAMP NOT NULL DEFAULT NOW(),
    updateDateTime TIMESTAMP,
    FOREIGN KEY (spending_id) REFERENCES spendings(id),
    FOREIGN KEY (payment_type_id) REFERENCES payment_types(id)
);

CREATE SEQUENCE se_ledgers
START WITH 1
INCREMENT BY 1;