-- liquibase formatted sql

-- changeset karmanov:20210918000000_added_field_in_processing_to_book-1
alter table book add in_processing boolean default false not null;
comment on column book.in_processing is 'Книга находится в обработке';
