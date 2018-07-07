------------------
-- Создание таблиц
------------------

-- Кузова
CREATE TABLE body
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Двигатели
CREATE TABLE engine
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Коробки передач
CREATE TABLE transmission
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Машины
CREATE TABLE car
(
  id 		serial 		PRIMARY KEY,
  name 		varchar(50) 	NOT NULL,
  body_id 	int 		NOT NULL REFERENCES body(id),
  eng_id 	int 		NOT NULL REFERENCES engine(id),
  trans_id 	int 		NOT NULL REFERENCES transmission(id)
);