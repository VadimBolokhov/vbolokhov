---------------------------------------
-- Заполнение таблиц начальными данными
---------------------------------------
INSERT INTO role (role_name) VALUES ('Administrator');
INSERT INTO role (role_name) VALUES ('Subscriber');
INSERT INTO rules (rule_desc) VALUES ('Add items');
INSERT INTO rules (rule_desc) VALUES ('Edit items');
INSERT INTO role_rules (role_id, rule_id) VALUES (1, 1);
INSERT INTO role_rules (role_id, rule_id) VALUES (1, 2);
INSERT INTO role_rules (role_id, rule_id) VALUES (2, 2);
INSERT INTO category (cat_name) VALUES ('First category');