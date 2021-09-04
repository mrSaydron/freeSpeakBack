-- liquibase formatted sql

-- changeset karmanov:20210327000000-1
alter table word add total_amount bigint null;
comment on column word.total_amount is 'Сколько раз слово встречается во всех книгах';

alter table word add picture_id varchar(255) null;
comment on column word.picture_id is 'Ссылка на картинку';

alter table word add audio_id varchar(255) null;
comment on column word.audio_id is 'Ссылка на озвучку';

