-- liquibase formatted sql

-- changeset karmanov:20210820100900_T-76-1
create sequence knowledge_progress_seq;
create table knowledge_progress
(
    id bigint default nextval('public.knowledge_progress_seq')
        not null constraint knowledge_progress_pk
        primary key,
    box_number int not null,
    type progress_type not null,
    knowledge real not null
);
alter sequence knowledge_progress_seq owned by knowledge_progress.id;

comment on column knowledge_progress.box_number is 'Номер коробки для которой указывается уровень знаний';
comment on column knowledge_progress.type is 'Тип коробки';
comment on column knowledge_progress.knowledge is 'Уровень знания, в от 0 до 1';
comment on table knowledge_progress is 'Связывает коробки и уровень знаний';

-- changeset karmanov:20210820100900_T-76-2
insert into knowledge_progress (box_number, type, knowledge) values (1, 'direct', 0.2);
insert into knowledge_progress (box_number, type, knowledge) values (2, 'direct', 0.4);
insert into knowledge_progress (box_number, type, knowledge) values (3, 'direct', 0.6);
insert into knowledge_progress (box_number, type, knowledge) values (4, 'direct', 0.7);
insert into knowledge_progress (box_number, type, knowledge) values (5, 'direct', 0.8);
insert into knowledge_progress (box_number, type, knowledge) values (6, 'direct', 0.9);
insert into knowledge_progress (box_number, type, knowledge) values (7, 'direct', 1.0);
