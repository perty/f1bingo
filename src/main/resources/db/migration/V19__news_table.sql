create table news
(
    id        bigserial primary key,
    content   varchar(100) not null,
    timestamp timestamp    not null default now()
);
