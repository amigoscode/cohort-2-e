create sequence patient_id_seq start with 1 increment by 1;

create table patient_entity
(
    id         integer                     not null,
    name       varchar(255)                not null,
    mrn        varchar(255)                not null,
    gender     gender                      not null,
    dob        timestamp(6) with time zone not null,
    created_at timestamp(6) with time zone not null,
    primary key (id),
    constraint patient_mrn_unique unique (mrn)
);