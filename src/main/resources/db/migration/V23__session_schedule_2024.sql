drop table if exists session_schedule;

create table session_schedule
(
    id        serial,
    summary   text      not null,
    starttime timestamp not null,
    endtime   timestamp not null,
    location  text      not null
);

insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ARAMCO GRAN PREMIO DE ESPAÑA 2024 - Practice 1', 'Spain', '20240621T113000Z', '20240621T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ARAMCO GRAN PREMIO DE ESPAÑA 2024 - Practice 2', 'Spain', '20240621T150000Z', '20240621T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ARAMCO GRAN PREMIO DE ESPAÑA 2024 - Practice 3', 'Spain', '20240622T103000Z', '20240622T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 ARAMCO GRAN PREMIO DE ESPAÑA 2024 - Qualifying', 'Spain', '20240622T140000Z', '20240622T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 ARAMCO GRAN PREMIO DE ESPAÑA 2024 - Race', 'Spain', '20240623T130000Z', '20240623T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS AUSTRIAN GRAND PRIX 2024 - Practice 1', 'Austria', '20240628T103000Z', '20240628T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS AUSTRIAN GRAND PRIX 2024 - Qualifyin', 'Austria', '20240629T140000Z', '20240629T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS AUSTRIAN GRAND PRIX 2024 - Sprint Race', 'Austria', '20240629T100000Z', '20240629T110000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS AUSTRIAN GRAND PRIX 2024 - Sprint Qu', 'Austria', '20240628T143000Z', '20240628T151400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS AUSTRIAN GRAND PRIX 2024 - Race', 'Austria', '20240630T130000Z', '20240630T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2024 - Practice', 'United Arab Emirates', '20241206T093000Z', '20241206T103000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2024 - Practice', 'United Arab Emirates', '20241206T130000Z', '20241206T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2024 - Practice', 'United Arab Emirates', '20241207T103000Z', '20241207T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2024 - Qualify', 'United Arab Emirates', '20241207T140000Z', '20241207T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2024 - Race', 'United Arab Emirates', '20241208T130000Z', '20241208T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ROLEX BELGIAN GRAND PRIX 2024 - Practice 1', 'Belgium', '20240726T113000Z', '20240726T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ROLEX BELGIAN GRAND PRIX 2024 - Practice 2', 'Belgium', '20240726T150000Z', '20240726T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 ROLEX BELGIAN GRAND PRIX 2024 - Practice 3', 'Belgium', '20240727T103000Z', '20240727T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 ROLEX BELGIAN GRAND PRIX 2024 - Qualifying', 'Belgium', '20240727T140000Z', '20240727T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 ROLEX BELGIAN GRAND PRIX 2024 - Race', 'Belgium', '20240728T130000Z', '20240728T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN DUTCH GRAND PRIX 2024 - Practice 1', 'Netherlands', '20240823T103000Z', '20240823T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN DUTCH GRAND PRIX 2024 - Practice 2', 'Netherlands', '20240823T140000Z', '20240823T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN DUTCH GRAND PRIX 2024 - Practice 3', 'Netherlands', '20240824T093000Z', '20240824T103000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 HEINEKEN DUTCH GRAND PRIX 2024 - Qualifying', 'Netherlands', '20240824T130000Z', '20240824T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 HEINEKEN DUTCH GRAND PRIX 2024 - Race', 'Netherlands', '20240825T130000Z', '20240825T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 PIRELLI GRAN PREMIO D’ITALIA 2024 - Practice 1', 'Italy', '20240830T113000Z', '20240830T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 PIRELLI GRAN PREMIO D’ITALIA 2024 - Practice 2', 'Italy', '20240830T150000Z', '20240830T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 PIRELLI GRAN PREMIO D’ITALIA 2024 - Practice 3', 'Italy', '20240831T103000Z', '20240831T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 PIRELLI GRAN PREMIO D’ITALIA 2024 - Qualifying', 'Italy', '20240831T140000Z', '20240831T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 PIRELLI GRAN PREMIO D’ITALIA 2024 - Race', 'Italy', '20240901T130000Z', '20240901T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2024 - Practice', 'Azerbaijan', '20240913T093000Z', '20240913T103000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2024 - Practice', 'Azerbaijan', '20240913T130000Z', '20240913T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2024 - Practice', 'Azerbaijan', '20240914T083000Z', '20240914T093000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2024 - Qualify', 'Azerbaijan', '20240914T120000Z', '20240914T130000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2024 - Race', 'Azerbaijan', '20240915T110000Z', '20240915T130000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2024 - Pract', 'Singapore', '20240920T093000Z', '20240920T103000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2024 - Pract', 'Singapore', '20240920T130000Z', '20240920T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2024 - Pract', 'Singapore', '20240921T093000Z', '20240921T103000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2024 - Qua', 'Singapore', '20240921T130000Z', '20240921T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2024 - Race', 'Singapore', '20240922T120000Z', '20240922T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 PIRELLI UNITED STATES GRAND PRIX 2024 - Practice 1', 'United States', '20241018T173000Z', '20241018T183000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 PIRELLI UNITED STATES GRAND PRIX 2024 - Sprint Qua', 'United States', '20241018T213000Z', '20241018T221400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 PIRELLI UNITED STATES GRAND PRIX 2024 - Sprint Race', 'United States', '20241019T180000Z', '20241019T190000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 PIRELLI UNITED STATES GRAND PRIX 2024 - Qualifying', 'United States', '20241019T220000Z', '20241019T230000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 PIRELLI UNITED STATES GRAND PRIX 2024 - Race', 'United States', '20241020T190000Z', '20241020T210000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAN PREMIO DE LA CIUDAD DE MÉXICO 2024 - Practice', 'Mexico', '20241025T183000Z', '20241025T193000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAN PREMIO DE LA CIUDAD DE MÉXICO 2024 - Practice', 'Mexico', '20241025T220000Z', '20241025T230000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAN PREMIO DE LA CIUDAD DE MÉXICO 2024 - Practice', 'Mexico', '20241026T173000Z', '20241026T183000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 GRAN PREMIO DE LA CIUDAD DE MÉXICO 2024 - Qualify', 'Mexico', '20241026T210000Z', '20241026T220000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 GRAN PREMIO DE LA CIUDAD DE MÉXICO 2024 - Race', 'Mexico', '20241027T200000Z', '20241027T220000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 LENOVO GRANDE PRÊMIO DE SÃO PAULO 2024 - Practice', 'Brazil', '20241101T143000Z', '20241101T153000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 LENOVO GRANDE PRÊMIO DE SÃO PAULO 2024 - Qualify', 'Brazil', '20241102T180000Z', '20241102T190000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 LENOVO GRANDE PRÊMIO DE SÃO PAULO 2024 - Sprint', 'Brazil', '20241101T183000Z', '20241101T191400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 LENOVO GRANDE PRÊMIO DE SÃO PAULO 2024 - Sprint Ra', 'Brazil', '20241102T140000Z', '20241102T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 LENOVO GRANDE PRÊMIO DE SÃO PAULO 2024 - Race', 'Brazil', '20241103T170000Z', '20241103T190000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS QATAR GRAND PRIX 2024 - Practice 1', 'Qatar', '20241129T133000Z', '20241129T143000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS QATAR GRAND PRIX 2024 - Qualifying', 'Qatar', '20241130T170000Z', '20241130T180000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS QATAR GRAND PRIX 2024 - Sprint Quali', 'Qatar', '20241129T173000Z', '20241129T181400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS QATAR GRAND PRIX 2024 - Sprint Race', 'Qatar', '20241130T130000Z', '20241130T140000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS QATAR GRAND PRIX 2024 - Race', 'Qatar', '20241201T170000Z', '20241201T190000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HUNGARIAN GRAND PRIX 2024 - Practice 1', 'Hungary', '20240719T113000Z', '20240719T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HUNGARIAN GRAND PRIX 2024 - Practice 2', 'Hungary', '20240719T150000Z', '20240719T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HUNGARIAN GRAND PRIX 2024 - Practice 3', 'Hungary', '20240720T103000Z', '20240720T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 HUNGARIAN GRAND PRIX 2024 - Qualifying', 'Hungary', '20240720T140000Z', '20240720T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 HUNGARIAN GRAND PRIX 2024 - Race', 'Hungary', '20240721T130000Z', '20240721T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DE MONACO 2024 - Practice 1', 'Monaco', '20240524T113000Z', '20240524T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DE MONACO 2024 - Practice 2', 'Monaco', '20240524T150000Z', '20240524T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DE MONACO 2024 - Practice 3', 'Monaco', '20240525T103000Z', '20240525T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 GRAND PRIX DE MONACO 2024 - Qualifying', 'Monaco', '20240525T140000Z', '20240525T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 GRAND PRIX DE MONACO 2024 - Race', 'Monaco', '20240526T130000Z', '20240526T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DU CANADA 2024 - Practice 1', 'Canada', '20240607T173000Z', '20240607T183000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DU CANADA 2024 - Practice 2', 'Canada', '20240607T210000Z', '20240607T220000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 GRAND PRIX DU CANADA 2024 - Practice 3', 'Canada', '20240608T163000Z', '20240608T173000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 GRAND PRIX DU CANADA 2024 - Qualifying', 'Canada', '20240608T200000Z', '20240608T210000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 GRAND PRIX DU CANADA 2024 - Race', 'Canada', '20240609T180000Z', '20240609T200000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 LENOVO CHINESE GRAND PRIX 2024 - Practice 1', 'China', '20240419T033000Z', '20240419T043000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 LENOVO CHINESE GRAND PRIX 2024 - Sprint Qualificat', 'China', '20240419T073000Z', '20240419T081400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 LENOVO CHINESE GRAND PRIX 2024 - Sprint Race', 'China', '20240420T030000Z', '20240420T040000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 LENOVO CHINESE GRAND PRIX 2024 - Qualifying', 'China', '20240420T070000Z', '20240420T080000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 LENOVO CHINESE GRAND PRIX 2024 - Race', 'China', '20240421T070000Z', '20240421T090000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 MSC CRUISES GRAN PREMIO DEL MADE IN ITALY E DELLEMI', 'Italy', '20240517T113000Z', '20240517T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 MSC CRUISES GRAN PREMIO DEL MADE IN ITALY E DELLEMI', 'Italy', '20240517T150000Z', '20240517T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 MSC CRUISES GRAN PREMIO DEL MADE IN ITALY E DELLEMI', 'Italy', '20240518T103000Z', '20240518T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 MSC CRUISES GRAN PREMIO DEL MADE IN ITALY E DELLE', 'Italy', '20240518T140000Z', '20240518T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 MSC CRUISES GRAN PREMIO DEL MADE IN ITALY E DELLEMI', 'Italy', '20240519T130000Z', '20240519T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 CRYPTO.COM MIAMI GRAND PRIX 2024 - Practice 1', 'United States', '20240503T163000Z', '20240503T173000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 CRYPTO.COM MIAMI GRAND PRIX 2024 - Sprint Qualific', 'United States', '20240503T203000Z', '20240503T211400Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 CRYPTO.COM MIAMI GRAND PRIX 2024 - Sprint Race', 'United States', '20240504T160000Z', '20240504T170000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 CRYPTO.COM MIAMI GRAND PRIX 2024 - Qualifying', 'United States', '20240504T200000Z', '20240504T210000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 CRYPTO.COM MIAMI GRAND PRIX 2024 - Race', 'United States', '20240505T200000Z', '20240505T220000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS BRITISH GRAND PRIX 2024 - Practice 1', 'United Kingdom', '20240705T113000Z', '20240705T123000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS BRITISH GRAND PRIX 2024 - Practice 2', 'United Kingdom', '20240705T150000Z', '20240705T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 QATAR AIRWAYS BRITISH GRAND PRIX 2024 - Practice 3', 'United Kingdom', '20240706T103000Z', '20240706T113000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 QATAR AIRWAYS BRITISH GRAND PRIX 2024 - Qualifying', 'United Kingdom', '20240706T140000Z', '20240706T150000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 QATAR AIRWAYS BRITISH GRAND PRIX 2024 - Race', 'United Kingdom', '20240707T140000Z', '20240707T160000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN SILVER LAS VEGAS GRAND PRIX 2024 - Practice', 'United States', '20241122T023000Z', '20241122T033000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN SILVER LAS VEGAS GRAND PRIX 2024 - Practice', 'United States', '20241122T060000Z', '20241122T070000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏎 FORMULA 1 HEINEKEN SILVER LAS VEGAS GRAND PRIX 2024 - Practice', 'United States', '20241123T023000Z', '20241123T033000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('⏱️ FORMULA 1 HEINEKEN SILVER LAS VEGAS GRAND PRIX 2024 - Qualif', 'United States', '20241123T060000Z', '20241123T070000Z');
insert into session_schedule (summary, location, starttime, endtime) values ('🏁 FORMULA 1 HEINEKEN SILVER LAS VEGAS GRAND PRIX 2024 - Race', 'United States', '20241124T060000Z', '20241124T080000Z');
