-- liquibase formatted sql

-- changeset karmanov:2021.12.14_1_create_and_fill_table_user_word_log_type
create table user_word_log_type
(
    id bigint not null,
    type varchar(255) not null,
    description varchar(255) not null,

    constraint user_word_log_type_pk
        primary key (id)
);
comment on table user_word_log_type is 'Типы действий над словами';

comment on column user_word_log_type.type is 'Идентификатор действия';
comment on column user_word_log_type.description is 'Описание типа действия';

insert into user_word_log_type (id, type, description) VALUES
(1, 'ADD_TO_DICTIONARY', 'слово добавлено в словарь пользователя'),
(2, 'REMOVE_FROM_DICTIONARY', 'слово удалено из словаря пользователя'),
(3, 'SUCCESS', 'пользователь верно ответил верно'),
(4, 'FAIL', 'пользователь ответил неверно'),
(5, 'SUCCESS_FROM_TEST', 'пользователь ответил верно в тесте'),
(6, 'FAIL_FROM_TEST', 'пользователь ответил неверно в тесте'),
(7, 'KNOW_FROM_TEST', 'по результату теста пользователь знает слово'),
(8, 'KNOW', 'пользователь отметил, что знает слово'),
(9, 'DO_NOT_KNOW', 'пользователь отметил, что не знает слово');
