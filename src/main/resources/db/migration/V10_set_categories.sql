update statement s
set category = 'DRIVER_OF_THE_DAY'
where s.text like '%Driver of the%';

update statement s
set category = 'FASTEST_LAP'
where s.text like '%snabbaste varv%';

update statement s
set category = 'POLE_POSITION'
where s.text like '%tar pole%';

update statement s
set category = 'RACE_WINNER'
where s.text like '%vinner';
