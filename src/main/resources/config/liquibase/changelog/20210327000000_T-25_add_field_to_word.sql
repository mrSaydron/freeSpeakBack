-- liquibase formatted sql

-- changeset karmanov:20210327000000-1
alter table word add total_amount bigint null comment 'Сколько раз слово встречается во всех книгах';
alter table word add picture_id tinytext null comment 'Ссылка на картинку';
alter table word add audio_id tinytext null comment 'Ссылка на озвучку';
