delete from fan;

INSERT INTO public.fan (id, name) VALUES (2, 'Ewa');
INSERT INTO public.fan (id, name) VALUES (3, 'Jeppe');
INSERT INTO public.fan (id, name) VALUES (1, 'Perty');

delete from race_weekend;

INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (1, 'Bahrains GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (2, 'Saudi-Arabiens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (3, 'Australiens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (4, 'Japans GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (5, 'Kinas GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (6, 'Miamis GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (7, 'Emilia Romagnas GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (8, 'Monacos GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (9, 'Kanadas GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (10, 'Spaniens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (11, 'Österrikes GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (12, 'Storbritanniens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (13, 'Ungerns GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (14, 'Belgiens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (15, 'Nederländernas GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (16, 'Italiens GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (17, 'Azerbajdzjans GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (18, 'Singapores GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (19, 'USAs GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (20, 'Mexikos GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (21, 'Brasiliens GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (22, 'Las Vegas GP', 'CLASSIC');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (23, 'Qatars GP', 'SPRINT');
INSERT INTO public.race_weekend (id, name, weekend_type) VALUES (24, 'Abu Dhabis GP', 'CLASSIC');

delete from statement;

INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (1, 'EN FERRARI TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (2, 'SARGEANT KVALAR SIST', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (3, 'MEDVETEN "TOW" TILL STALLKOMPISEN', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (4, 'NÅGON SÄGER "PIP" UNDER KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (5, 'RÖDFLAGG UNDER KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (6, 'HAAS ELLER WILLIAMS GÅR TILL Q3', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (7, 'EN RED BULL ÅKER UT I Q1', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (8, 'EN FÖRARE KVALAR FÖR FÖRSTA GÅNGEN BLAND 10 BÄSTA', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (9, 'EN AV A-BILARNA TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (10, 'EN MERCEDES TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (11, 'VERSTAPPEN TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (12, 'PEREZ TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (13, 'EN ASTON MARTIN TAR POLE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (14, 'EN FÖRARE MISSAR CHANS TILL SNABB TID PGA TRAFIK', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (15, 'STROLL BLIR EN AV DE TRE BÄSTA I KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (16, 'EN TREDJEFÖRARE FÅR KÖRA KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (17, 'DET BLIR ETT REGNKVAL (= INTERMEDIATES SITTER PÅ NÅGON BIL)', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (18, 'EN BIL TAPPAR EN DEL', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (19, 'EN AV A-BILARNA ORSAKAR GULFLAGG UNDER KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (20, 'EN MCLAREN KOMMER SIST I KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (21, 'EN FÖRARE FÅR STRAFF INNAN KVAL', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (22, 'EN MCLAREN GÅR TILL Q3', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (23, 'EN ASTON MARTIN ÅKER UT I Q1', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (24, 'EN FERRARI ÅKER UT I Q1', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (25, 'EN MERCEDES ÅKER UT I Q1', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (26, 'EN FÖRARE FÅR SIN TID STRUKEN PGA TRACK LIMITS', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (27, 'EN FÖRARE FÅR STRAFF FÖR IMPEDING UNDER KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (28, 'EN AV ALPINEBILARNA KVALAR TOPP-3', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (29, 'KVALSTARTEN BLIR UPPSKJUTEN AV NÅGON ANLEDNING', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (30, 'EN BIL BRYTER UNDER KVALET', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (31, 'EN AV TOPPFÖRARNA (RB, FERRARI, AM, M) ÅKER UT I Q2', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (32, 'RACE CONTROL: INVESTIGATE "...MAXIMUM LAPTIME"', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (33, 'EN FÖRARE KLAGAR PÅ ANNAN FÖRARE', false, true, true, false);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (34, 'HULKENBERG OCH MAGNUSEN RÅKAR I LUVEN PÅ VARANDRA', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (35, 'RICCIARDO FÅR KÖRA ETT RACE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (36, 'SCHUMACHER FÅR KÖRA ETT RACE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (37, 'GASLY OCH OCON KOMMER IHOP SIG UNDER RACET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (38, 'EN BIL BÖRJAR BRINNA (MEN ALLT GÅR BRA)', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (39, 'HAMILTON OCH VERSTAPPEN KRASCHAR I VARANDRA', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (40, 'TSUNODA KRASCHAR', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (41, 'EN FÖRARE FÅR 5 SEKUNDERS TILLÄGG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (42, 'TVÅ BILAR ELLER FLER TAR UT VARANDRA I EN SEKVENS', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (43, 'SAINZ ÄR INTE ÖVERENS MED STALLET OM STRATEGI', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (44, 'GULFLAGG PÅ FÖRSTA VARVET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (45, 'ETT TEAM GÖR 3 DEPÅSTOPP', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (46, 'POLESITTER VINNER INTE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (47, 'NÅGON UTANFÖR TOPP-3 PÅ PALLEN (RB, F, M)', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (48, 'STROLL TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (49, 'ALBON TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (50, 'TVÅ VIRTUELLA SÄKERHETSBILAR UNDER RACET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (51, 'EN FÖRARE FÅR STALLORDER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (52, 'EN FÖRARE SÄGER "PIP" UNDER RACET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (53, 'ALONSO VINNER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (54, 'OCON KOMMER PÅ PALLEN ', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (55, 'PIASTRI TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (56, 'HAMILTON TAR FLER POÄNG ÄN PEREZ', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (57, 'HAAS TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (58, 'RUSSEL KOMMER PÅ PALLEN ', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (59, 'VERSTAPPEN BRYTER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (60, 'LECLERC VINNER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (61, 'BOTTAS TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (62, 'WILLIAMS TAR POÄNG', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (63, 'SÄKERHETSBIL UNDER RACET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (64, 'PEREZ VÄGRAR LYDA STALLORDER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (65, 'SAINZ VINNER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (66, 'NÅGON GISSAR PÅ RÄTT DEPÅTID', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (67, 'MINST 4 FÖRARE BRYTER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (68, 'EN FÖRARE SLUTAR +/- 7 PLATSER FRÅN KVAL', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (69, 'EN McLAREN SLUTAR TOPP 10', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (70, 'EN FÖRARE KLAGAR PÅ GREPPNIVÅN', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (71, 'FERRARI TAR ETT KONTRAPRODUKTIVT BESLUT', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (72, 'EN BIL MÅSTE BYTA NOS', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (73, 'DET REGNAR UNDER RACET (= INTERMEDIATES PÅ)', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (74, 'EN FÖRARE FÅR STRAFF EFTER KVAL OCH INNAN RACE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (75, 'PEREZ FÅR POÄNG FÖR SNABBASTE VARV', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (76, 'HAMILTON BLIR DRIVER OF THE DAY', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (77, 'RACET BLIR RÖDFLAGGAT', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (78, 'VIRTUELL SÄKERHETSBIL ÅTMINSTONE 1 GÅNG UNDER RACET', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (79, 'INGEN BIL BRYTER', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (80, 'EN BIL STARTAR FRÅN DEPÅN', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (81, 'EN FÖRARE FÅR EN VARNING PGA TRACKLIMITS', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (82, 'ALLA PÅ PALLEN HAR VARIT ELLER ÄR VÄRLDSMÄSTARE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (83, 'HAMILTON KLAGAR PÅ STRATEGIN', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (84, 'EN FÖRARE KLAGAR PÅ ANNAN FÖRARE', true, false, false, true);
INSERT INTO public.statement (id, text, race, qualifying, sprint_shootout, sprint_race) VALUES (85, 'DET BLIR RACE MELLAN TEAMKOLLEGOR (RÄCKER MED KORT SÅDANT)', true, false, false, true);