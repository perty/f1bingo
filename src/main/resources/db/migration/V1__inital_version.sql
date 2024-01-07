create table fan
(
    id   bigserial primary key,
    name varchar(20) not null
);

create table race_weekend
(
    id           bigserial primary key,
    name         varchar(100) not null,
    weekend_type varchar(20)  not null
);

create table weekend_palette
(
    id      bigserial primary key,
    fan     int not null,
    foreign key (fan) references fan (id),
    weekend int not null,
    foreign key (weekend) references race_weekend (id)
);

create table bingo_card
(
    id              bigserial primary key,
    session         varchar(20) not null,
    weekend_palette int         not null,
    foreign key (weekend_palette) references weekend_palette (id)
);

create table statement
(
    id              bigserial primary key,
    text            varchar(100) not null,
    race            bool         not null,
    qualifying      bool         not null,
    sprint_shootout bool         not null,
    sprint_race     bool         not null
);

create table bingo_card_statement
(
    id         bigserial primary key,
    row        int not null,
    col        int not null,
    bingo_card int not null,
    foreign key (bingo_card) references bingo_card (id),
    statement  int not null,
    foreign key (statement) references statement (id)
);

create table result
(
    id        bigserial primary key,
    weekend   int  not null,
    foreign key (weekend) references race_weekend (id),
    statement int  not null,
    foreign key (statement) references statement (id),
    result    bool not null
);

