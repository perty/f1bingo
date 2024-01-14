alter table race_weekend add column startDate timestamp not null default '1970-01-01 00:00:00';
alter table race_weekend add column endDate timestamp not null default '1970-01-01 00:00:00';
alter table race_weekend add column country text default '';
alter table race_weekend add column track text default '';

