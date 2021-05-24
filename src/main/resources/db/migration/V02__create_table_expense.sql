CREATE TABLE expense(
id INTEGER NOT NULL PRIMARY KEY,
title VARCHAR(80),
description VARCHAR(255),
cost DECIMAL NOT NULL,
due_date DATE NOT NULL,
paid BOOLEAN,
invoice_date DATE,
service_period_start DATE,
service_period_end DATE,
periodicity VARCHAR(50),
payment_method VARCHAR(50),
expense_type_id INTEGER,
FOREIGN KEY (expense_type_id) REFERENCES expense_type(id)
);

CREATE SEQUENCE se_expense
START WITH 1
INCREMENT BY 1;