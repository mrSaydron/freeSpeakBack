-- liquibase formatted sql

-- changeset karmanov:2022.02.13_remove_column_from_user_has_sentences
alter table user_has_sentences drop column fail_last_date;
