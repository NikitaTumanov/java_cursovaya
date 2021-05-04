CREATE TABLE IF NOT EXISTS category
(
    id    SERIAL PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL
);
CREATE TABLE IF NOT EXISTS product
(
    id    SERIAL PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    volume INTEGER,
    price INTEGER,
    category_id INTEGER
);
CREATE TABLE IF NOT EXISTS users
(
    id    SERIAL PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    password VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL
);
CREATE TABLE IF NOT EXISTS basket
(
    id    SERIAL PRIMARY KEY ,
    product_id INTEGER ,
    users_id INTEGER
);