-- liquibase formatted sql

-- changeset karmanov:20210529100800_T-45
alter table word add transcription tinytext null comment 'Транскрипция';
alter table word add audio_file_requested bool not null default false comment 'Помечает, был ли запрошен перевод';
