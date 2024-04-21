-- column category will not null with default value 'NONE'
update statement
set category='NONE'
where category is null;

alter table statement
alter column category set not null;
