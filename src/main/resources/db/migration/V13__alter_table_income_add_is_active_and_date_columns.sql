ALTER TABLE income ADD COLUMN active BOOLEAN default true;
alter table income add column type VARCHAR(50);
alter table income add column insert_date date default now();
alter table income add column update_date date;