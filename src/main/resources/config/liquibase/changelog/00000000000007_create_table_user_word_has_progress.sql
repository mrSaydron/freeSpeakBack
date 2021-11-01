-- liquibase formatted sql

-- changeset karmanov:00000000000007_create_table_user_word_has_progress
create sequence user_word_has_progress_seq;
create table user_word_has_progress
(
    id bigint not null,
    user_word_id bigint not null,
    type varchar(32) not null,
    successful_attempts integer not null default 0,
    box_number integer not null default 0,
    fail_last_date timestamp null,
    successful_last_date timestamp null,

    constraint user_word_has_progress_pk
        primary key (id),
    constraint user_word_progress_user_word_id_fk
        foreign key (user_word_id) references user_word (id)
            on delete cascade
);
alter sequence user_word_has_progress_seq owned by user_word_has_progress.id;

comment on table user_word_has_progress is 'Прогресс пользователя по слову';

comment on column user_word_has_progress.user_word_id is 'Идентификатор слова пользователя';
comment on column user_word_has_progress.type is 'Тип изучения (прямой, обратный перевод и др)';
comment on column user_word_has_progress.successful_attempts is 'Успешных попыток';
comment on column user_word_has_progress.box_number is 'Номер коробки';
comment on column user_word_has_progress.fail_last_date is 'Дата последнего не верного ответа';
comment on column user_word_has_progress.successful_last_date is 'Дата последнего верного ответа';

