-- liquibase formatted sql

-- changeset karmanov:2022.01.29_add_column_to_user_word_has_progress
alter table user_word_has_progress add fail_attempts int default 0 not null;

comment on column user_word_has_progress.fail_attempts is 'Неудачных ответов';
