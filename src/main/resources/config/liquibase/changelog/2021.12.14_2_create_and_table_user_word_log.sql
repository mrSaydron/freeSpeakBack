-- liquibase formatted sql

-- changeset karmanov:2021.12.14_2_create_table_user_word_log
create sequence user_word_log_seq;
create table user_word_log
(
    id bigint not null,
    type_id bigint not null,
    test_vocabulary_id bigint null,
    word_id bigint not null,
    user_id bigint not null,
    date timestamp not null,

    constraint user_word_log_pk
        primary key (id),
    constraint user_word_log_type_id_fk
        foreign key (type_id) references user_word_log_type (id)
            on delete cascade,
    constraint user_word_log_test_vocabulary_id_fk
        foreign key (test_vocabulary_id) references test_vocabulary (id)
            on delete cascade,
    constraint user_word_log_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade,
    constraint user_word_log_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade
);
alter sequence user_word_log_seq owned by user_word_log.id;
comment on table user_word_log is 'Лог действий пользователя со словами';

comment on column user_word_log.type_id is 'Тип совершенного действия';
comment on column user_word_log.test_vocabulary_id is 'Идентификатор теста словарного запаса';
comment on column user_word_log.word_id is 'Идентификатор слова';
comment on column user_word_log.user_id is 'Идентификатор пользователя';
comment on column user_word_log.date is 'Время совершения действия';
