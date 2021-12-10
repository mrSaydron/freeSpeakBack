-- liquibase formatted sql

-- changeset karmanov:00000000000070_create_table_test_vocabulary_check
create sequence test_vocabulary_check_seq;
create table test_vocabulary_check
(
    id bigint default nextval('public.test_vocabulary_check_seq'),
    test_vocabulary_id bigint not null,
    word_id bigint not null,
    success boolean not null,

    constraint test_vocabulary_check_pk
        primary key (id),
    constraint test_vocabulary_check_test_vocabulary_id_fk
        foreign key (test_vocabulary_id) references test_vocabulary (id)
            on delete cascade,
    constraint test_vocabulary_check_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade
);
alter sequence test_vocabulary_check_seq owned by test_vocabulary_check.id;

comment on table test_vocabulary_check is 'Результат ответов пользователя в тесте на словарный запас';

comment on column test_vocabulary_check.test_vocabulary_id is 'Идентификатор попытки теста';
comment on column test_vocabulary_check.word_id is 'Идентификатор слова';
comment on column test_vocabulary_check.success is 'Результат';
