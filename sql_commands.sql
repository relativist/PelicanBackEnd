create table public.pelican_user
(
  id serial not null
    constraint pelican_user_pkey
    primary key,
  login       text,
  email       text,
  password      text,
  name   text
);

alter table public.pelican_user
  owner to auto3n;



create table public.pelican_category
(
  id serial not null
    constraint pelican_category_pkey
    primary key,
    user_id int,
    category_parent_id int,
    name text,
    simple boolean,
    score int,
    disposable boolean,
    disposable_capacity int,
    disposable_done int,
    deprecated boolean
);

alter table public.pelican_category
  owner to auto3n;



create table public.pelican_event
(
  id serial not null
    constraint pelican_event_pkey
    primary key,
  user_id int,
  category_id int,
  score int,
  date text
);

alter table public.pelican_event
  owner to auto3n;




CREATE SEQUENCE hibernate_sequence START 1;









