-- liquibase formatted sql

-- changeset karmanov:T-26-1:20210328230900
create table service_data
(
    key_data varchar(255) not null comment 'Ключ (Идентификатор)',
    value text null comment 'Значения',
    constraint service_data_pk
        primary key (key_data)
) comment 'Данные сервиса';

-- changeset karmanov:T-26-2:20210328230900
insert into service_data(key_data, value) VALUES ('totalWords', '1');
