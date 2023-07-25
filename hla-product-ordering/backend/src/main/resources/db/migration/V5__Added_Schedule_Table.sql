create sequence schedule_id_seq start with 1 increment by 1;
create table schedule_entity
(
    id         integer not null,
    patient_id integer not null,
    status     status  not null,
    primary key (id)
);