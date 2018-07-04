------------------
-- Создание таблиц
------------------
CREATE TABLE state
(
  state_id 	serial		PRIMARY KEY,
  state_name	varchar(50)	NOT NULL
);

CREATE TABLE category
(
  cat_id 	serial		PRIMARY KEY,
  cat_name	varchar(50)	NOT NULL
);

CREATE TABLE role
(
  role_id 	serial 		PRIMARY KEY,
  role_name 	varchar(50)	NOT NULL
);

CREATE TABLE rules
(
  rule_id	serial		PRIMARY KEY,
  rule_desc	varchar(50)	NOT NULL
);

CREATE TABLE role_rules
(
  role_id	integer		NOT NULL REFERENCES role(role_id),
  rule_id	integer		NOT NULL REFERENCES rules(rule_id)
);
CREATE TABLE users
(
  user_id 	serial 		primary key,
  user_name 	varchar(50) 	NOT NULL,
  role_id 	integer 	NOT NULL REFERENCES role(role_id)
);

CREATE TABLE items
(
  item_id 	serial		PRIMARY KEY,
  item_name	varchar(50)	NOT NULL,
  cat_id	integer		NOT NULL REFERENCES category(cat_id),
  state_id	integer		NOT NULL REFERENCES state(state_id),
  user_id	integer		UNIQUE REFERENCES users(user_id)
);

CREATE TABLE comments
(
  com_id 	serial		PRIMARY KEY,
  com_text	text		NOT NULL,
  item_id	integer		NOT NULL REFERENCES items(item_id)
);

CREATE TABLE attachs
(
  att_id 	serial		PRIMARY KEY,
  att_file	varchar(50)	NOT NULL,
  item_id	integer		NOT NULL REFERENCES items(item_id)
);
