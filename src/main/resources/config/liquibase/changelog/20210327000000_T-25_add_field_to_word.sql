-- liquibase formatted sql

-- changeset karmanov:20210327000000-1
alter table word add total_amount bigint null comment 'Сумма по всем книгам';
alter table word add url_picture tinytext null comment 'ссылка на картинку';
alter table word add url_audio tinytext null comment 'ссылка на озвучку';
