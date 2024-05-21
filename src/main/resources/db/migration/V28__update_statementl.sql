alter table statement alter category set default 'NONE';

INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race)
VALUES (129, 'Däcket sitter inte på efter stopp', true, false, false, false);

update statement
set enabled = false
where text in ('En förare kvalar för första gången bland de 10 bästa',
               'En McLaren kommer sist i kvalet',
               'Alonso vinner',
               'En förare hyllar fansen',
               'Alla på pallen har varit eller är världsmästare',
               'Hamilton och Russel kör in i varandra');

update statement
set text = 'En förare klagar på bilen'
where text = 'Alonso klagar på bilen';

