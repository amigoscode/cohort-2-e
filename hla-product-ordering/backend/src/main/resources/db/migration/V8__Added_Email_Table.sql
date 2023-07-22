create sequence email_id_seq start with 1 increment by 1;

create table email_entity (
  id integer not null,
  provider_id integer not null,
  user_id integer not null,
  created_at timestamp(6) with time zone not null,
  content varchar(255) not null,
  primary key (id),
  constraint fk_provider foreign key (provider_id) references provider_entity(id),
  constraint fk_user foreign key (user_id) references user_entity(id)
);