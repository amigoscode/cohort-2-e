DROP TYPE IF EXISTS status CASCADE;
create type status as enum ('DONE','REVIEW');
DROP TYPE IF EXISTS state CASCADE;
create type state as enum ('DONE','REVIEW');