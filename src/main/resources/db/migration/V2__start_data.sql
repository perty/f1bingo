insert into fan (id, name) values (1, 'perty');
insert into fan (id, name) values (2, 'ewa');

insert into race_weekend (id, name, weekend_type) values (1, 'Spanien', 'CLASSIC');
insert into race_weekend (id, name, weekend_type) values (2, 'Italien', 'SPRINT');

insert into statement (id, text, race, qualifying, sprint_shootout, sprint_race) values (1, 'Verstappen tar pole', false, true, true, false);
insert into statement (id, text, race, qualifying, sprint_shootout, sprint_race) values (2, 'Verstappen vinner', true, false, false, true);