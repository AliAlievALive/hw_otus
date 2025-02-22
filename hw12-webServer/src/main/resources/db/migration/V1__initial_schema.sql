create table if not exists client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id int
);

create table if not exists address
(
    id   serial not null primary key,
    street varchar
);

create table if not exists phone
(
    id   serial not null primary key,
    number varchar,
    client_id bigint
);

create table if not exists account
(
    id   serial not null primary key,
    role varchar,
    login varchar,
    password varchar
);

CREATE SEQUENCE client_id_seq;
ALTER TABLE client ALTER COLUMN id SET DEFAULT nextval('client_id_seq');