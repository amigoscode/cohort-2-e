create sequence note_id_seq start with 1 increment by 1;

create table note_entity
(
    id               integer                     not null,
    schedule_id      integer                     not null,
    schedule_version integer                     not null,
    note             text                        not null,
    created_at       timestamp(6) with time zone not null,
    created_by       integer                     not null,
    primary key (id),
    constraint fk_user foreign key (created_by) references user_entity (id),
    constraint fk_schedule foreign key (schedule_id) references schedule_entity (id)
);