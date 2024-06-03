
DROP SEQUENCE IF EXISTS product_seq;

CREATE SEQUENCE product_seq START 1 INCREMENT 1;

DROP TABLE IF EXISTS products CASCADE;

create table products (
    price numeric(38,2),
    id bigint not null,
    name varchar(255),
    primary key (id));


DROP SEQUENCE IF EXISTS user_seq;

create sequence user_seq start 1 increment 1;

DROP table if exists users cascade;

create table users (
    id bigint not null,
    email varchar(255),
    username varchar(255),
    password varchar(255),
    role varchar(255)
    check (role in ('ADMIN','USER')),
    primary key (id));