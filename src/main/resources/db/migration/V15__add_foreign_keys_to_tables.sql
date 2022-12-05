ALTER TABLE income_categories
    ADD CONSTRAINT fk_users_income_categories FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE spending_categories
    ADD CONSTRAINT fk_users_spending_categories FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE payment_types
    ADD CONSTRAINT fk_users_payment_types FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE incomes
    ADD CONSTRAINT fk_income_categories_incomes FOREIGN KEY (income_category_id) REFERENCES income_categories(id);

ALTER TABLE spendings
    ADD CONSTRAINT fk_spending_categories_spendings FOREIGN KEY (spending_category_id) REFERENCES spending_categories(id);

ALTER TABLE fixed_income_funds
    ADD CONSTRAINT fk_spendings_fixed_income_funds FOREIGN KEY (spending_id) REFERENCES spendings(id);

ALTER TABLE ledger_registries
    ADD CONSTRAINT fk_spendings_ledgers FOREIGN KEY (spending_id) REFERENCES spendings(id);

ALTER TABLE ledger_registries
    ADD CONSTRAINT fk_payment_type_ledgers FOREIGN KEY (payment_type_id) REFERENCES payment_types(id);

ALTER TABLE fund_details
    ADD CONSTRAINT fk_ledger_fund_details FOREIGN KEY (ledger_id) REFERENCES ledger_registries(id);