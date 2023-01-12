insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(33,89,5,43,1,'12-01-2023', 1);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(3,9,51,42,10,'12-01-2023', 2);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(20,81,53,47,17,'12-01-2023', 3);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(33,83,53,43,13,'12-01-2023', 4);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(35,84,36,46,16,'12-01-2023', 5);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(22,76,75,74,55,'12-01-2023', 6);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(12,46,78,55,21,'12-01-2023', 7);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(22,2,7,3,12,'12-01-2023', 8);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(4,23,10,90,36,'12-01-2023', 9);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(33,76,48,21,4,'12-01-2023', 10);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(22,71,61,57,13,'14-01-2023', 1);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(31,7,1,2,19,'14-01-2023', 2);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(14,76,67,55,52,'14-01-2023', 3);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(37,81,33,89,88,'14-01-2023', 4);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(26,84,90,16,66,'14-01-2023', 5);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(20,6,5,79,65,'14-01-2023', 6);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(17,49,74,50,29,'14-01-2023', 7);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(12,9,7,30,15,'14-01-2023', 8);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(42,43,11,90,35,'14-01-2023', 9);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(3,6,8,1,41,'14-01-2023', 10);

insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(3,65,8,10,6,'17-01-2023', 1);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(67,21,9,5,32,'17-01-2023', 2);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(1,65,3,54,60,'17-01-2023', 3);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(23,4,31,7,5,'17-01-2023', 4);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(9,76,90,3,2,'17-01-2023', 5);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(2,65,8,6,1,'17-01-2023', 6);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(7,5,34,41,4,'17-01-2023', 7);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(8,7,65,80,90,'17-01-2023', 8);
insert into extraction(first_number, second_number, third_number, fourth_number, fifth_number,extraction_date, wheel_id)
values(21,67,4,3,8,'17-01-2023', 9);


////////////////////////////////////////////////////////////////

insert into superenalotto(milano,bari,palermo,roma,napoli,firenze,extraction_date)
values((select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'milano' and e.extraction_date = '12-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'bari'and e.extraction_date = '12-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'palermo' and e.extraction_date = '12-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'roma' and e.extraction_date = '12-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'napoli' and e.extraction_date = '12-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'firenze' and e.extraction_date = '12-01-2023'
),'2023-01-12');






insert into superenalotto(milano,bari,palermo,roma,napoli,firenze,extraction_date)
values((select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'milano' and e.extraction_date = '14-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'bari'and e.extraction_date = '14-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'palermo' and e.extraction_date = '14-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'roma' and e.extraction_date = '14-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'napoli' and e.extraction_date = '14-01-2023'
),(select e.first_number
from extraction e
inner join wheel w
on e.wheel_id = w.id
where LOWER(w.city) = 'firenze' and e.extraction_date = '14-01-2023'
),'2023-01-12');

////////////////////////////////////////////////////////////////

INSERIMENTO DATI TABELLA WHEEL

insert into wheel( address, city)
values('corso Garibaldi 4', 'Bari');
insert into wheel( address, city)
values('corso Matteotti 25', 'Napoli');
insert into wheel( address, city)
values('corso della repubblica 4', 'Milano');
insert into wheel(address, city)
values('via Gramsci 1', 'Palermo');
insert into wheel(address, city)
values('corso Garibaldi 22', 'Roma');
insert into wheel(address, city)
values('viale Mortara 5', 'Firenze');
insert into wheel(address, city)
values('via vigevano 34', 'Nazionale');
insert into wheel(address, city)
values('via del carmine 5A', 'Cagliari');
insert into wheel(address, city)
values('via tortona 12', 'Venezia');
insert into wheel(address, city)
values('corso Garibaldi 15', 'Torino');
