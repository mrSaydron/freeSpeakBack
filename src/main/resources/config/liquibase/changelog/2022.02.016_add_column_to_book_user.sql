-- liquibase formatted sql

-- changeset karmanov:2022.02.016_add_column_to_book_user
alter table book_user add finish_date timestamp null;

comment on column book_user.finish_date is 'Отмечает дату, когда пользователь прочитал книгу';
