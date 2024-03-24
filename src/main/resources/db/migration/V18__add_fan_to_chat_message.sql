insert into fan (id, name) values (0, 'old');

alter table chat_message
add column fan bigint not null default 0
references fan(id);


