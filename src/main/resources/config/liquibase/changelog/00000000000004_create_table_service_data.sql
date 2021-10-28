-- liquibase formatted sql

-- changeset karmanov:00000000000004_create_table_service_data
create table service_data
(
    key_data varchar(255) not null,
    value text null,

    constraint service_data_pk
        primary key (key_data)
);

comment on column service_data.key_data is 'Ключ (Идентификатор)';
comment on column service_data.value is 'Значения';
comment on table service_data is 'Данные сервиса';
