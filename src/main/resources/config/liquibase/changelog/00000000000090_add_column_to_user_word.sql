-- liquibase formatted sql

-- changeset karmanov:00000000000090_add_column_to_user_word
alter table user_word add from_test boolean default false not null;
comment on column user_word.from_test is 'Уровень проставлен по результатам теста на словарный запас';
