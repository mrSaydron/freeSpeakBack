-- liquibase formatted sql

-- changeset id:20210114000000-1 author:karmanov
alter table book_user add last_open_date timestamp without time zone null;
comment on column book_user.last_open_date is 'Последняя дата открытия книги';
