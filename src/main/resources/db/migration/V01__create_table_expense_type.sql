CREATE TABLE expense_type(
    id integer not null primary key,
    name varchar(80) not null,
    category varchar(50) not null
);

INSERT INTO expense_type (id, name,category) VALUES (1, 'Rent', 'HOUSE_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (2, 'Electricity', 'HOUSE_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (3, 'Water', 'HOUSE_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (4, 'Internet', 'HOUSE_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (5, 'Food/Grocery/Clean', 'HOUSE_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (6, 'Bus', 'TRANSPORTATION');
INSERT INTO expense_type (id, name,category) VALUES (7, 'Uber/99/Taxi', 'TRANSPORTATION');
INSERT INTO expense_type (id, name,category) VALUES (8, 'Fuel', 'TRANSPORTATION');
INSERT INTO expense_type (id, name,category) VALUES (9, 'Plane Ticket', 'TRANSPORTATION');
INSERT INTO expense_type (id, name,category) VALUES (10, 'Boat', 'TRANSPORTATION');
INSERT INTO expense_type (id, name,category) VALUES (11, 'Clothing', 'PERSONAL_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (12, 'Leisure', 'PERSONAL_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (13, 'Travel Expenses', 'PERSONAL_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (14, 'Streaming', 'PERSONAL_EXPENSES');
INSERT INTO expense_type (id, name,category) VALUES (15, 'Others', 'OTHERS');
INSERT INTO expense_type (id, name,category) VALUES (16, 'Credit Card', 'OTHERS');

