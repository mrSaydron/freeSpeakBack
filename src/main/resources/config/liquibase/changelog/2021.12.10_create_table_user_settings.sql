-- liquibase formatted sql

-- changeset karmanov:2021.12.10_create_table_user_settings
create table user_settings
(
    user_id bigint not null,
    tested_vocabulary boolean not null default false,

    constraint user_settings_pk
        primary key (user_id),
    constraint user_settings_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade
);
comment on table user_settings is 'Настройки пользователя';

comment on column user_settings.user_id is 'Идентификатор пользователя';
comment on column user_settings.tested_vocabulary is 'Проходил ли пользователь тест на словарный запас';
