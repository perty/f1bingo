create table remember_me
(
    selector   varchar(255) not null,
    token_hash  varchar(255) not null,
    fan_id      bigserial    not null,
    expires_at  timestamp   not null,
    last_used_at timestamp   not null
)
