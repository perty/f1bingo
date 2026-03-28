-- When deleteing a race weekend, we want to delete all associated data as well.
alter table result
    drop constraint result_weekend_fkey;

alter table result
    add constraint result_weekend_fkey
        foreign key (weekend)
            references race_weekend (id)
            on delete cascade;


alter table verified_session
    drop constraint verified_session_race_weekend_fkey;

alter table verified_session
    add constraint verified_session_race_weekend_fkey
        foreign key (race_weekend)
            references race_weekend (id)
            on delete cascade;


alter table verified_statement
    drop constraint verified_statement_verified_session_fkey;

alter table verified_statement
    add constraint verified_statement_verified_session_fkey
        foreign key (verified_session)
            references verified_session (id)
            on delete cascade;


alter table weekend_palette
    drop constraint weekend_palette_weekend_fkey;

alter table weekend_palette
    add constraint weekend_palette_weekend_fkey
        foreign key (weekend)
            references race_weekend (id)
            on delete cascade;


alter table bingo_card
    drop constraint bingo_card_weekend_palette_fkey;

alter table bingo_card
    add constraint bingo_card_weekend_palette_fkey
        foreign key (weekend_palette)
            references weekend_palette (id)
            on delete cascade;

alter table bingo_card_statement
    drop constraint bingo_card_statement_bingo_card_fkey;

alter table bingo_card_statement
    add constraint bingo_card_statement_bingo_card_fkey
        foreign key (bingo_card)
            references bingo_card (id)
            on delete cascade;
