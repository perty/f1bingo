update statement set text = 'Det blir ett regnkval = Intermediates på någon bil'
                 where text = 'Det blir ett regnkval = Intermediates sitter på någon bil';
update statement set text = 'Det blir race mellan teamkollegor'
                 where text = 'Det blir race mellan teamkollegor (räcker med kort sådant)';
update statement set text = 'En bil touchar/kraschar i en barriär'
                 where text = 'En bil touchar eller kraschar i en vägg eller liknande';
update statement set text = 'En förare vägrar lyda stallorder'
                 where text = 'Perez vägrar lyda stallorder';
update statement set text = 'Långsamt depåstopp >4 sek'
                 where text = 'Ett stall strular med ett depåstopp';

INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (116, 'En förare säger "The car feels different"',true,true,true,true);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (117, 'Depån uppmanar till omkörning', true, false, false, true);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (118, 'En McLaren på pallen', true, false, false, true);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (119, 'Ett stall gör ett "double stack"', true, false, false, false);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (120, 'En förare använder en avåkningszon', true, true, true, true);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (121, 'Båda bilarna i ett stall bryter', true, false, false, true);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (122, 'Ett stall döms för "Unsafe release"',  true, true, false, false);
INSERT INTO statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (123, 'Hamilton hyllar fansen', true, true, true, true);

ALTER TABLE statement
    ALTER COLUMN text TYPE VARCHAR(52);

