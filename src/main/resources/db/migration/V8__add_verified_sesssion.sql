create table verified_session
(
    id bigserial primary key,
    created timestamp not null default now(),
    race_weekend int not null references race_weekend(id),
    session text not null
);
create unique index unique_verified_session on verified_session (race_weekend, session);

create table verified_statement
(
    id bigserial primary key,
    verified_session int not null references verified_session(id),
    statement int not null references statement(id)
);

