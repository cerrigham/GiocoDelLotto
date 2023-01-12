CREAZIONE TABELLA EXTRACTION

create table if not exists extraction(
id serial primary key,
first_number int not null,
second_number int not null,
third_number int not null,
fourth_number int not null,
fifth_number int not null,
extraction_date date not null,
wheel_id int ,
foreign key(wheel_id) references wheel(id));
---------------------------------------------------------------------

CREAZIONE TABELLA WHEEL

create table if not exists wheel(
id serial primary key,
address varchar (100) not null,
city varchar (30) not null
);

--------------------------------------------------------------------------------

CREAZIONE TABELLA SUPERENALOTTO

create table if not exists superenalotto(
id serial primary key,
milano int not null,
bari int not null,
palermo int not null,
roma int not null,
napoli int not null,
firenze int not null,
extraction_date date not null);