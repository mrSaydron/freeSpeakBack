-- liquibase formatted sql

-- changeset karmanov:T-29-1:20210420230200
alter table user_word_progress add fail_last_date timestamp without time zone null;
alter table user_word_progress add success_last_date timestamp without time zone null;

comment on column user_word_progress.fail_last_date is 'Дата последнего не правильно ответа';
comment on column user_word_progress.success_last_date is 'Дата последнего правильного ответа';
