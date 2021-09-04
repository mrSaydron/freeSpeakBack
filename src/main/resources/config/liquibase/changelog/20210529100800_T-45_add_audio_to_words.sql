-- liquibase formatted sql

-- changeset karmanov:20210529100800_T-45
alter table word add transcription varchar(255) null;
alter table word add audio_file_requested bool not null default false;

comment on column word.transcription is 'Транскрипция';
comment on column word.audio_file_requested is 'Помечает, был ли запрошен перевод';
