create sequence user_id_seq start with 1 increment by 1;

create table user_entity
(
    id         integer                     not null,
    email      varchar(255)                not null,
    name       varchar(255)                not null,
    password   varchar(255)                not null,
    role       varchar(255)                not null,
    created_at timestamp(6) with time zone not null,
    primary key (id),
    constraint user_email_unique unique (email)
);