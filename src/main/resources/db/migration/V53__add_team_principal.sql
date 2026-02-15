alter table team
    add column team_chief varchar(200);

update team set team_chief = 'Flavio Briatore' where code = 'alpine';
update team set team_chief = 'Adrian Newey' where code = 'astonMartin';
update team set team_chief = 'Jonathan Wheatley' where code = 'audi';
update team set team_chief = 'Graeme Lowdon' where code = 'cadillac';
update team set team_chief = 'Frédéric Vasseur' where code = 'ferrari';
update team set team_chief = 'Ayao Komatsu' where code = 'haas';
update team set team_chief = 'Andrea Stella' where code = 'mclaren';
update team set team_chief = 'Toto Wolff' where code = 'mercedes';
update team set team_chief = 'Alan Permane' where code = 'racingBulls';
update team set team_chief = 'Laurent Mekies' where code = 'redBull';
update team set team_chief = 'James Vowles' where code ='williams';
