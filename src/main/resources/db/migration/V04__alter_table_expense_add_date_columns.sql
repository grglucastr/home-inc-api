alter table expense add column paid_date date;
alter table expense add column insert_date date default now();
alter table expense add column update_date date;