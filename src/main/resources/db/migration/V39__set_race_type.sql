delete from verified_statement;
delete from verified_session;
delete from race_weekend where startdate < '2025_01_01';

update race_weekend set weekend_type = 'CLASSIC' where weekend_type = 'TBD';

update race_weekend set weekend_type = 'SPRINT' where
name in (
        'Chinas GP',
        'Miami GP',
        'USAs GP',
        'Brazils GP'
        'Qatars GP'
        );