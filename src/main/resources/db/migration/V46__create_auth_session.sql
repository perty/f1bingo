create table auth_session
(
    id         varchar(255) not null,
    fan_id     bigserial    not null,
    expires_at timestamp    not null
)
