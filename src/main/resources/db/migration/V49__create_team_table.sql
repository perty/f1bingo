create table team
(
    id            bigserial primary key,
    code          varchar(20)  not null unique,
    display_name  varchar(20)  not null,
    official_name varchar(500) not null,
    nationality   varchar(60)  not null,
    engine        varchar(60)  not null
);
insert into team (code, display_name, official_name, nationality, engine)
values ('alpine', 'Alpine', 'BWT Alpine Formula One Team', 'france', 'Mercedes'),
       ('astonMartin', 'Aston Martin', 'Aston Martin Aramco Formula One Team', 'greatBritain', 'Honda'),
       ('audi', 'Audi', 'Audi Revolut F1 Team', 'germany', 'Audi'),
       ('cadillac', 'Cadillac', 'Cadillac Formula 1 Team', 'usa', 'Ferrari'),
       ('ferrari', 'Ferrari', 'Scuderia Ferrari HP', 'italy', 'Ferrari'),
       ('haas', 'Haas', 'TGR Haas F1 Team', 'usa', 'Ferrari'),
       ('mclaren', 'McLaren', 'McLaren Mastercard F1 Team', 'greatBritain', 'Mercedes'),
       ('mercedes', 'Mercedes', 'Mercedes-AMG PETRONAS Formula One Team', 'germany', 'Mercedes'),
       ('racingBulls', 'Racing Bulls', 'Visa Cash App Racing Bulls Formula One Team', 'italy', 'RedBullFord'),
       ('redBull', 'Red Bull', 'Oracle Red Bull Racing', 'austria', 'RedBullFord'),
       ('williams', 'Williams', 'Atlassian Williams F1 Team', 'greatBritain', 'Mercedes');

