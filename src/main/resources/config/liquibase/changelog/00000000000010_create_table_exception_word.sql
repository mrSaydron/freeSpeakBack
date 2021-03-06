-- liquibase formatted sql

-- changeset karmanov:00000000000010_create_table_word_exception
create sequence exception_word_seq;
create table exception_word
(
    id bigint default nextval('public.exception_word_seq') not null,
    word varchar(255) not null,
    part_of_speech varchar(32) not null,
    target_word varchar(255) not null,
    target_part_of_speech varchar(32) not null,

    constraint exception_word_pk
        primary key (id)
);
alter sequence exception_word_seq owned by exception_word.id;
create index exception_word_word_part_of_speech_index on exception_word (word, part_of_speech);

comment on table exception_word is 'Список исключений, с итоговыми словами';

comment on column exception_word.word is 'Слово для исключения';
comment on column exception_word.part_of_speech is 'Часть речи для исключения (или любая)';
comment on column exception_word.target_word is 'Итоговое слово';
comment on column exception_word.target_part_of_speech is 'Итоговая часть речи';
