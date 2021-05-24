ALTER TABLE expense DROP COLUMN insert_date_time;
ALTER TABLE expense ADD COLUMN insert_date_time TIMESTAMPTZ;
ALTER TABLE expense ALTER COLUMN insert_date_time SET DEFAULT current_timestamp;