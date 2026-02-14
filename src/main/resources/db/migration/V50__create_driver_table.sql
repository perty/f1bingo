create table driver
(
    id          bigserial primary key,
    code        varchar(5)   not null,
    number      int          not null,
    yellow_cam  boolean      not null,
    full_name   varchar(200) not null,
    nationality varchar(50)  not null,
    length      int          not null,
    born        date         not null
);

insert into driver (code, number, yellow_cam, full_name, nationality, length, born)
values
       ('alb', 23, false, 'Alexander Albon', 'thailand', 186, '1996-03-23'),
       ('alo', 14, false, 'Fernando Alonso', 'spain', 171, '1981-07-29'),
       ('ant', 12, false, 'Kimi Antonelli', 'italy', 172, '2006-08-25'),
       ('bea', 87, false, 'Oliver Bearman', 'greatBritain', 184, '2005-05-08'),
       ('bor', 5, false, 'Gabriel Bortoleto', 'brazil', 184, '2004-10-14'),
       ('bot', 77, false, 'Valtteri Bottas', 'finland', 173, '1989-08-28'),
       ('col', 43, false, 'Franco Colapinto', 'argentina', 174, '2003-05-27'),
       ('gas', 10, false, 'Pierre Gasly', 'france', 177, '1996-02-07'),
       ('had', 6, false, 'Isack Hadjar', 'france', 167, '2004-09-28'),
       ('ham', 44, false, 'Lewis Hamilton', 'greatBritain', 174, '1985-01-07'),
       ('hul', 27, false, 'Nico Hulkenberg', 'germany', 184, '1987-08-19'),
       ('law', 30, false, 'Liam Lawson', 'newZealand', 174, '2002-02-11'),
       ('lec', 16, false, 'Charles Leclerc', 'monaco', 180, '1997-10-16'),
       ('lin', 41, false, 'Arvid Lindblad', 'greatBritain', 173, '2007-08-08'),
       ('nor', 1, false, 'Lando Norris', 'greatBritain', 170, '1999-11-13'),
       ('oco', 31, false, 'Esteban Ocon', 'france', 186, '1996-09-17'),
       ('per', 11, false, 'Sergio Perez', 'mexico', 173, '1990-01-26'),
       ('pia', 81, false, 'Oscar Piastri', 'australia', 178, '2001-04-06'),
       ('rus', 63, false, 'George Russell', 'greatBritain', 185, '1998-02-15'),
       ('sai', 55, false, 'Carlos Sainz', 'spain', 178, '1994-09-01'),
       ('str', 18, false, 'Lance Stroll', 'canada', 182, '1998-10-29'),
       ('ver', 3, false, 'Max Verstappen', 'netherlands', 181, '1997-09-30');
