update statement
set category = 'QUALIFYING_LAST'
where text = 'En McLaren kommer sist i kvalet';
update statement
set category = 'QUALIFYING_LAST'
where text = 'Sargeant kvalar sist';

INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race, category)
VALUES (124, 'En Alpine kvalar sist', false, true, true, false, 'QUALIFYING_LAST');


