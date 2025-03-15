update race_weekend set name = 'Spainens GP' where name = 'Spains GP';
update race_weekend set name = 'Emilia-Romagna GP' where startdate='2025-05-16 00:00:00.000000';
update race_weekend set name = 'Kanadas GP' where name = 'Canadas GP';
update race_weekend set name = 'Österrikes GP' where name = 'Austrias GP';
update race_weekend set name = 'Storbritanniens GP' where name = 'Uniteds GP';
update race_weekend set name = 'Belgiens GP' where name = 'Belgiums GP';
update race_weekend set name = 'Ungerns GP' where name = 'Hungarys GP';
update race_weekend set name = 'Nederländernas GP' where name = 'Netherlandss GP';
update race_weekend set name = 'Italiens GP' where name = 'Italys GP';
update race_weekend set name = 'Azerbajdzjans GP' where name = 'Azerbaijans GP';
update race_weekend set name = 'Brasiliens GP' where name = 'Brazils GP';
update race_weekend set name = 'Las Vegas GP' where name = 'Las_Vega GP';
update race_weekend set name = 'Abu Dhabis GP' where name = 'Abu_Dhabis GP';

update race_weekend set weekend_type = 'SPRINT' where id in (37, 45, 47);
