-- liquibase formatted sql

-- changeset karmanov:00000000000008_create_table_knowledge_progress
create sequence knowledge_progress_seq;
create table knowledge_progress
(
    id bigint default nextval('public.knowledge_progress_seq') not null,
    box_number int not null,
    type varchar(32) not null,
    knowledge real not null,

    constraint knowledge_progress_pk
        primary key (id)
);
alter sequence knowledge_progress_seq owned by knowledge_progress.id;

comment on table knowledge_progress is 'Связывает коробки и уровень знаний';

comment on column knowledge_progress.box_number is 'Номер коробки для которой указывается уровень знаний';
comment on column knowledge_progress.type is 'Тип коробки';
comment on column knowledge_progress.knowledge is 'Уровень знания, в от 0 до 1';
