CREATE DATABASE gioco_del_lotto;

CREATE TABLE IF NOT EXISTS gioco_del_lotto.public.wheel (
id SERIAL PRIMARY KEY,
address VARCHAR (100) NOT NULL,
city VARCHAR (30) NOT NULL
);

CREATE TABLE IF NOT EXISTS gioco_del_lotto.public.extraction (
id SERIAL PRIMARY KEY,
first_number INT NOT NULL,
second_number INT NOT NULL,
third_number INT NOT NULL,
fourth_number INT NOT NULL,
fifth_number INT NOT NULL,
extraction_date DATE NOT NULL,
wheel_id INT,
FOREIGN KEY (wheel_id) REFERENCES wheel(id));

CREATE TABLE IF NOT EXISTS gioco_del_lotto.public.superenalotto (
id SERIAL PRIMARY KEY,
milano INT NOT NULL,
bari INT NOT NULL,
palermo INT NOT NULL,
roma INT NOT NULL,
napoli INT NOT NULL,
firenze INT NOT NULL,
extraction_date DATE NOT NULL);