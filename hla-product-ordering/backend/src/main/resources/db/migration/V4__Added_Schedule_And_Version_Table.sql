create sequence schedule_id_seq start with 1 increment by 1;

create table schedule_entity
(
    id         integer not null,
    patient_id integer not null,
    status     enum    not null,
    primary key (id)
);

create sequence version_id_seq start with 1 increment by 1;

create table version_entity
(
    id              integer                     not null,
    version         integer                     not null,
    schedule_id     integer                     not null,
    updated_by      integer                     not null,
    updated_at      timestamp(6) with time zone not null,
    state           enum                        not null,
    start_date      timestamp(6) with time zone not null,
    end_date        timestamp(6) with time zone not null,
    status          enum                        not null,
    quantity        integer                     not null,
    schedule_period integer                     not null,
    primary key (id),
    constraint fk_user foreign key (updated_by) references user_entity(id),
    constraint fk_schedule foreign key (schedule_id) references schedule_entity(id)
);