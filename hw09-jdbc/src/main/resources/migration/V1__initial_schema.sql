create table client
(
    id   bigserial primary key,
    name varchar(50)
);

create table manager
(
    no   bigserial primary key,
    label varchar(50),
    param1 varchar(50)
);