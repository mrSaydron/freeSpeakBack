-- liquibase formatted sql

-- changeset karmanov:00000000000006_create_table_user_word
create sequence user_word_seq;
create table user_word
(
    id bigint default nextval('public.user_word_seq') not null,
    user_id bigint not null,
    word_id bigint not null,

    constraint user_word_pk
        primary key (id),
    constraint user_word_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade,
    constraint user_word_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade
);
alter sequence user_word_seq owned by user_word.id;

comment on table user_word is 'Связывает пользователя и его слова';

comment on column user_word.user_id is 'Идентификатор пользователя';
comment on column user_word.word_id is 'Идентификатор слова';
