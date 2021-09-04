-- liquibase formatted sql

-- changeset karmanov:T-25-2:20210327154900
create table user_dictionary
(
    id bigint null,
    user_id bigint not null,
    base_language varchar(255) null,
    target_language varchar(255) null,
    constraint user_dictionary_pk
        primary key (id),
    constraint user_dictionary_jhi_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade
);

comment on column user_dictionary.id is 'Идентификатор';
comment on column user_dictionary.user_id is 'Идентификатор пользователя';
comment on column user_dictionary.base_language is 'Исходный язык';
comment on column user_dictionary.target_language is 'Целевой язык';
comment on table user_dictionary is 'Словарь пользователя';

-- changeset karmanov:T-25-3:20210327154900
create table user_dictionary_has_word
(
    id bigint null,
    user_dictionary_id bigint not null,
    word_id bigint not null,
    priority int null,
    constraint user_dictionary_has_word_pk
        primary key (id),
    constraint user_dictionary_has_word_user_dictionary_id_fk
        foreign key (user_dictionary_id) references user_dictionary (id),
    constraint user_dictionary_has_word_word_id_fk
        foreign key (word_id) references word (id)
);

comment on column user_dictionary_has_word.id is 'Идентификатор';
comment on column user_dictionary_has_word.user_dictionary_id is 'идентификатор словаря пользователя';
comment on column user_dictionary_has_word.word_id is 'идентификатор слова';
comment on column user_dictionary_has_word.priority is 'приоритет изучения слова';
comment on table user_dictionary_has_word is 'Связка словаря пользователя со словом';

-- changeset karmanov:T-25-4:20210327154900
create type progress_type as enum ('direct', 'reverse', 'test', 'listening', 'spelling');

-- changeset karmanov:T-25-5:20210327154900
create table user_word_progress
(
    id bigint null,
    user_dictionary_has_word_id bigint not null,
    type progress_type not null,
    successful_attempts int not null default 0,
    box_number int not null default 0,
    constraint user_word_progress_pk
        primary key (id),
    constraint user_word_progress_user_dictionary_has_word_id_fk
        foreign key (user_dictionary_has_word_id) references user_dictionary_has_word (id)
);

comment on column user_word_progress.id is 'Идентификатор';
comment on column user_word_progress.user_dictionary_has_word_id is 'идентификатор связки словаря и слова';
comment on column user_word_progress.type is 'тип изучения (перевод прямой/обратный, тест, написание, аудирование)';
comment on column user_word_progress.successful_attempts is 'успешных ответов';
comment on column user_word_progress.box_number is 'номер коробки для изученного слова';
comment on table user_word_progress is 'Хранит прогресс изучения слова';
