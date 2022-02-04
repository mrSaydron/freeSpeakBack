-- liquibase formatted sql

-- changeset karmanov:2022.01.29_add_column_to_user_has_sentences
alter table user_has_sentences add mark_date timestamp null;

comment on column user_has_sentences.mark_date is 'Дата на которую предложение отмеченно для изучения';
