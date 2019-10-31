--------------
-- Drop tables
--------------

DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS model;
DROP TABLE IF EXISTS transmission;
DROP TABLE IF EXISTS gearbox;
DROP TABLE IF EXISTS engine;
DROP TABLE IF EXISTS body;
DROP TABLE IF EXISTS make;

----------------
-- Create tables
----------------

-- Makes
CREATE TABLE make
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Bodies
CREATE TABLE body
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Gearboxes
CREATE TABLE gearbox
(
  id 	serial 		PRIMARY KEY,
  name varchar(50) NOT NULL
);

-- Engines
CREATE TABLE engine
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Transmissions
CREATE TABLE transmission
(
  id 	serial 		PRIMARY KEY,
  name 	varchar(50) 	NOT NULL
);

-- Models
CREATE TABLE model
(
  id         serial       PRIMARY KEY,
  name       varchar(50)  NOT NULL,
  make_id    int          NOT NULL REFERENCES make(id),
  body_id    int          NOT NULL REFERENCES body(id),
  engine_id  int          NOT NULL REFERENCES engine(id),
  trans_id   int          NOT NULL REFERENCES transmission(id)
);

-- Users
CREATE TABLE users
(
  id serial PRIMARY KEY,
  login varchar(20) UNIQUE NOT NULL,
  password varchar(50) NOT NULL,
  firstname varchar(20) NOT NULL,
  lastname  varchar(20) NOT NULL,
  email varchar(256) NOT NULL,
  reg_date timestamp NOT NULL DEFAULT NOW(),
  role int NOT NULL
);

-- Cars
CREATE TABLE car
(
  id serial PRIMARY KEY,
  mod_id int NOT NULL REFERENCES model(id),
  gear_id    int          NOT NULL REFERENCES gearbox(id),
  odometer real NOT NULL DEFAULT 0,
  price real DEFAULT 0,
  color int NOT NULL,
  sold boolean NOT NULL DEFAULT false,
  description text NULL,
  post_date timestamp NOT NULL DEFAULT NOW(),
  user_id int NOT NULL REFERENCES users(id)
);

--------------
-- Fill tables
--------------

-- Users
INSERT INTO users(login, password, firstname, lastname, email, role) VALUES
('root', 'root', 'root', 'root', 'root@email.com', 0),
('user1', 'password', 'Vadim', 'Bolokhov', 'some@email.com', 1);

-- Makes
INSERT INTO make(name) VALUES
('Opel'), ('Dodge'), ('Lada');

-- Bodies
INSERT INTO body(name) VALUES
('Supermini'), ('Hatchback'), ('MPV'), ('Sedan'), ('Coupe'), ('SUV');

-- Gearboxes
INSERT INTO gearbox(name)
VALUES ('manual'), ('automatic');

-- Transmissions
INSERT INTO transmission(name)
VALUES ('Front-wheel drive'), ('Rear-wheel drive'), ('All-wheel drive');

-- Engines
INSERT INTO engine(name)
VALUES ('Gasoline'), ('Diesel'), ('Electric'), ('Hybrid');

-- Models
INSERT INTO model(name, make_id, body_id, engine_id, trans_id) VALUES
('Insignia OPC', 1, 4, 1, 3),
('Astra 5 Doors', 1, 2, 1, 1),
('Viper', 2, 5, 1, 2),
('Journey', 2, 6, 1, 1),
('Dart', 2, 5, 1, 1),
('Challenger SRT Demon', 2, 5, 1, 2),
('Kalina', 3, 4, 1, 1),
('Priora', 3, 2, 1, 1);

-- Cars
INSERT INTO car(mod_id, gear_id, odometer, price, color, user_id, description) VALUES
(1, 2, 50000, 10000, 1, 2, 'My first car'),
(3, 1, 50000, 40000, 2, 2, 'My second car'),
(5, 2, 100000, 10000, 3, 2, 'My third car'),
(7, 1, 150000, 2000, 4, 2, 'My fourth car');