--------------------------------------------------------------
-- 1. Вывести список всех машин и все привязанные к ним детали
--------------------------------------------------------------
SELECT  c.name AS car_name,
	b.name AS body_name,
	e.name AS eng_name,
	t.name AS trans_type
FROM car AS c, body AS b, engine AS e, transmission AS t
WHERE c.body_id = b.id AND c.eng_id = e.id AND c.trans_id = t.id;

----------------------------------------------------------------
-- 2. Вывести отдельно детали, которые не используются в машине,
-- кузова, двигатели, коробки передач.
----------------------------------------------------------------

-- Кузова
SELECT b.name
FROM body AS b LEFT OUTER JOIN car AS c
ON b.id = c.body_id WHERE c.id IS NULL;
-- Двигатели
SELECT e.name
FROM engine AS e LEFT OUTER JOIN car AS c
ON e.id = c.eng_id WHERE c.id IS NULL;
-- Коробки передач
SELECT t.name
FROM transmission AS t LEFT OUTER JOIN car AS c
ON t.id = c.trans_id WHERE c.id IS NULL;