alter table email_entity
add sent_at timestamp(6) with time zone,
add schedule_id integer;