-- liquibase formatted sql

-- changeset karmanov:00000000000050_create_table_test_vocabulary
create sequence test_vocabulary_seq;
create table test_vocabulary
(
    id bigint default nextval('public.test_vocabulary_seq'),
    user_id bigint not null,
    try_number integer not null,
    try_date timestamp not null,
    result_count integer null,
    result_word_id bigint null,

    constraint test_vocabulary_pk
        primary key (id),
    constraint test_vocabulary_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade,
    constraint test_vocabulary_word_id_fk
        foreign key (result_word_id) references word (id)
            on delete cascade
);
alter sequence test_vocabulary_seq owned by test_vocabulary.id;

comment on table test_vocabulary is 'Хранит попытки тестирования словарного запасу у пользователя';

comment on column test_vocabulary.user_id is 'Идентификатор пользователя';
comment on column test_vocabulary.try_number is 'Порядковый номер попытки пользователя';
comment on column test_vocabulary.try_date is 'Дата попытки пользователя';
comment on column test_vocabulary.result_count is 'Результат в колличестве слов';
comment on column test_vocabulary.result_word_id is 'Идентификатор слова который принят за границу';
