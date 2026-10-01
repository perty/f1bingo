delete
from race_weekend
where startdate > '2026-10-01';

select setval('race_weekend_id_seq', (select max(id) from race_weekend) );

INSERT INTO race_weekend(name, startdate, enddate, weekend_type, country, track)
VALUES ('BAHRAIN GP', '2026-10-02 04:30', '2026-10-04 09:00', 'CLASSIC', 'Bahrain', 'Sepang International Circuit'),
       ('SINGAPORE GP', '2026-10-09 08:30', '2026-10-11 14:00', 'SPRINT', 'Singapore', 'Marina Bay Street Circuit'),
       ('UNITED STATES GP', '2026-10-23 17:30', '2026-10-25 22:00', 'CLASSIC', 'USA', 'cotas'),
       ('MEXICO GP', '2026-10-30', '2026-11-01', 'CLASSIC', 'Mexico', 'hermanos'),
       ('Brazil GP', '2026-11-06', '2026-11-08', 'CLASSIC', 'brazil', 'interlagos'),
       ('LAS VEGAS GP', '2026-11-20', '2026-11-22', 'CLASSIC', 'Usa', 'las-vegas'),
       ('QATAR GP', '2026-11-27', '2026-11-29', 'CLASSIC', 'Qatar', 'Lusail International Circuit'),
       ('ABU DHABI GP', '2026-12-04', '2026-12-06', 'CLASSIC', 'Abu-dhabi', 'Yas Marina Circuit');