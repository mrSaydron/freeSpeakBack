-- liquibase formatted sql

-- changeset karmanov:00000000000003_create_table_word
create sequence word_seq;
create table word
(
    id bigint default nextval('public.word_seq') not null,
    word varchar(255) not null,
    part_of_speech varchar(16) not null,
    translate varchar(255) null,
    transcription varchar(255) null,
    total_amount bigint not null default 0,
    audio_id varchar(255) null,
    audio_file_requested boolean not null default false,

    constraint user_dictionary_pk
        primary key (id)
);
alter sequence word_seq owned by word.id;

comment on table word is 'Слово';

comment on column word.word is 'Написание слова';
comment on column word.part_of_speech is 'Часть речи';
comment on column word.translate is 'Переводы';
comment on column word.transcription is 'Транскрипция';
comment on column word.total_amount is 'Сколько слово встречается во всех книгах';
comment on column word.audio_id is 'Идентификатор озвучки';
comment on column word.audio_file_requested is 'Был ли запрошен файл озвучки';
