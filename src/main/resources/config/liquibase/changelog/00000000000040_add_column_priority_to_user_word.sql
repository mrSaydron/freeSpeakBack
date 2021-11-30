-- liquibase formatted sql

-- changeset karmanov:00000000000040_add_column_priority_to_user_word
alter table user_word add priority integer default 2147483647 not null;
comment on column user_word.priority is 'Приоритет для слова, чем меньше тем приоритетней';
