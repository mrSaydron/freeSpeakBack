-- liquibase formatted sql

-- changeset karmanov:00000000000060_create_table_test_vocabulary_answer
create sequence test_vocabulary_answer_seq;
create table test_vocabulary_answer
(
    id bigint default nextval('public.test_vocabulary_answer_seq'),
    test_vocabulary_id bigint not null,
    word_id bigint not null,
    success boolean not null,

    constraint test_vocabulary_answer_pk
        primary key (id),
    constraint test_vocabulary_answer_test_vocabulary_id_fk
        foreign key (test_vocabulary_id) references test_vocabulary (id)
            on delete cascade,
    constraint test_vocabulary_answer_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade
);
alter sequence test_vocabulary_answer_seq owned by test_vocabulary_answer.id;

comment on table test_vocabulary_answer is 'Результат ответов пользователя в тесте на словарный запас';

comment on column test_vocabulary_answer.test_vocabulary_id is 'Идентификатор попытки теста';
comment on column test_vocabulary_answer.word_id is 'Идентификатор слова';
comment on column test_vocabulary_answer.success is 'Результат';
