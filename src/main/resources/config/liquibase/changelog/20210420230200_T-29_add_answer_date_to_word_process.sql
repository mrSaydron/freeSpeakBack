-- liquibase formatted sql

-- changeset karmanov:T-29-1:20210420230200
alter table user_word_progress add fail_last_date datetime null comment 'Дата последнего не правильно ответа';
alter table user_word_progress add success_last_date datetime null comment 'Дата последнего правильного ответа';
