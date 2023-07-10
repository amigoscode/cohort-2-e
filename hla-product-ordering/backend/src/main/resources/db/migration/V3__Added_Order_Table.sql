create sequence order_id_seq start with 1 increment by 1;

create table order_entity (
    email_id integer not null,
    id integer not null,
    schedule_id integer not null,
    schedule_version integer not null,
    received timestamp(6) with time zone not null,
    scheduled_for timestamp(6) with time zone not null,
    primary key (id)
);