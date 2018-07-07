--------------------
-- Заполнение таблиц
--------------------

-- Кузова
INSERT INTO body(name) VALUES ('supermini');
INSERT INTO body(name) VALUES ('hatchback');
INSERT INTO body(name) VALUES ('MPV');
INSERT INTO body(name) VALUES ('saloon');

-- Двигатели
INSERT INTO engine(name) VALUES ('straight');
INSERT INTO engine(name) VALUES ('V-type');
INSERT INTO engine(name) VALUES ('boxer');

-- Коробки передач
INSERT INTO transmission(name) VALUES ('manual');
INSERT INTO transmission(name) VALUES ('automatic');

-- Машины
INSERT INTO car(name, body_id, eng_id, trans_id)
VALUES ('first', 1, 1, 1);
INSERT INTO car(name, body_id, eng_id, trans_id)
VALUES ('second', 3, 3, 1);