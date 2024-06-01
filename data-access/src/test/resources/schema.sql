-- CREATE TABLE customer (
--     id BIGINT auto_increment PRIMARY KEY,
--     name varchar(100) not null,
--     email varchar(255) not null,
--     UNIQUE (name)
-- );

DROP TABLE IF EXISTS customer;

CREATE TABLE customer(
                         id bigserial not null primary key,
                         name varchar(100) not null unique,
                         email varchar(255) not null
);