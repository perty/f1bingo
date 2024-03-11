create table chat_message
(
    id        bigserial primary key,
    message   text      not null,
    timestamp timestamp not null
);
