-- liquibase formatted sql

-- changeset karmanov:00000000000007_create_table_user_word_progress
create table user_word_progress
(
    user_id bigint not null,
    word_id bigint not null,
    type varchar(32) not null,
    successful_attempts integer not null default 0,
    box_number integer not null default 0,
    fail_last_date timestamp null,
    successful_last_date timestamp null,

    constraint user_word_progress_pk
        primary key (user_id, word_id, type),
    constraint user_word_progress_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade,
    constraint user_word_progress_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade
);

comment on table user_word_progress is 'Прогресс пользователя по слову';

comment on column user_word_progress.user_id is 'Идентификатор пользователя';
comment on column user_word_progress.word_id is 'Идентификатор слова';
comment on column user_word_progress.type is 'Тип изучения (прямой, обратный перевод и др)';
comment on column user_word_progress.successful_attempts is 'Успешных попыток';
comment on column user_word_progress.box_number is 'Номер коробки';
comment on column user_word_progress.fail_last_date is 'Дата последнего не верного ответа';
comment on column user_word_progress.successful_last_date is 'Дата последнего верного ответа';

