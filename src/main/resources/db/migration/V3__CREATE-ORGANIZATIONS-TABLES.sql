create table organizations
(
    ong_id        uuid primary key unique,
    ong_name      varchar(100) not null,
    tax_number    varchar(14)  not null unique,
    description   text,
    website       varchar(255),
    email         varchar(100) not null unique,
    password      varchar(30)  not null,
    contact_email varchar(100),
    contact_phone varchar(20),
    verified      boolean      not null default false,
    location      bigint       not null,
    working_hours varchar(20),
    donation_info text,
    created_at    timestamp    not null,
    updated_at    timestamp    not null,
    foreign key (location) references address (location_id)
);

CREATE TABLE organization_social_media
(
    ong_id UUID         NOT NULL,
    platform        VARCHAR(255) NOT NULL,
    handle          VARCHAR(255),
    PRIMARY KEY (ong_id, platform),
    FOREIGN KEY (ong_id) REFERENCES organizations (ong_id)
);
