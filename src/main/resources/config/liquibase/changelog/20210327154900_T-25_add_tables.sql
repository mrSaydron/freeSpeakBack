-- liquibase formatted sql

-- changeset karmanov:T-25-2:20210327154900
create table user_dictionary
(
    id bigint null comment 'Идентификатор',
    user_id bigint not null comment 'Идентификатор пользователя',
    base_language tinytext null comment 'Исходный язык',
    target_language tinytext null comment 'Целевой язык',
    constraint user_dictionary_pk
        primary key (id),
    constraint user_dictionary_jhi_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade
) comment 'Словарь пользователя';

-- changeset karmanov:T-25-3:20210327154900
create table user_dictionary_has_word
(
    id bigint null comment 'Идентификатор',
    user_dictionary_id bigint not null comment 'идентификатор словаря пользователя',
    word_id bigint not null comment 'идентификатор слова',
    priority int null comment 'приоритет изучения слова',
    constraint user_dictionary_has_word_pk
        primary key (id),
    constraint user_dictionary_has_word_user_dictionary_id_fk
        foreign key (user_dictionary_id) references user_dictionary (id),
    constraint user_dictionary_has_word_word_id_fk
        foreign key (word_id) references word (id)
) comment 'Связка словаря пользователя со словом';

-- changeset karmanov:T-25-4:20210327154900
create table user_word_progress
(
    id bigint null comment 'Идентификатор',
    user_dictionary_has_word_id bigint not null comment 'идентификатор связки словаря и слова',
    type enum('direct', 'reverse', 'test', 'listening', 'spelling') not null comment 'тип изучения (перевод прямой/обратный, тест, написание, аудирование)',
    successful_attempts int not null default 0 comment 'успешных ответов',
    box_number int not null default 0 comment 'номер коробки для изученного слова',
    constraint user_word_progress_pk
        primary key (id),
    constraint user_word_progress_user_dictionary_has_word_id_fk
        foreign key (user_dictionary_has_word_id) references user_dictionary_has_word (id)
) comment 'Хранит прогресс изучения слова';
