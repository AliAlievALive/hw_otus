create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id int
);

create table address
(
    id   serial not null primary key,
    street varchar
);

create table phone
(
    id   serial not null primary key,
    number varchar,
    client_id bigint
)