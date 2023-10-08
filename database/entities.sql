--create type system_role as enum('hero', '');

create table users (
    user_id int primary key,
    login text not null,
    password text not null,
    nick_name text not_null
);