CREATE SEQUENCE address_seq
    INCREMENT BY 1
    START WITH 1
    NO CYCLE;

create table address
(
    location_id bigint primary key unique not null,
    country     varchar(50)               not null,
    state       varchar(100)              not null,
    district    varchar(50)               not null,
    street      varchar(50)               not null,
    number      varchar(5),
    complement  varchar(30),
    zip         varchar(8),
    created_at  timestamp                 not null,
    updated_at  timestamp                 not null
);

create table users
(
    user_id       uuid primary key unique,
    name          varchar(50)  not null,
    surname       varchar(50)  not null,
    username      varchar(15)  not null,
    email         varchar(100) not null unique,
    password      varchar(30)  not null,
    mobile_phone  varchar(15) unique,
    interested_in text         not null,
    pet_age_range text         not null,
    location      bigint       not null unique,
    created_at    timestamp    not null,
    updated_at    timestamp    not null,
    foreign key (location) references address (location_id)
);