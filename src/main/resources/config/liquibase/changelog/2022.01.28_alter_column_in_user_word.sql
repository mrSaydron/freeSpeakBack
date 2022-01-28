-- liquibase formatted sql

-- changeset karmanov:2022.01.28_alter_column_in_user_word
alter table user_word alter column priority type bigint using priority::bigint;
alter table user_word alter column priority set default 9223372036854775807;
