-- liquibase formatted sql

-- changeset id:20210114000000-1 author:karmanov
alter table book_user add last_open_date datetime null comment 'Последняя дата открытия книги';
