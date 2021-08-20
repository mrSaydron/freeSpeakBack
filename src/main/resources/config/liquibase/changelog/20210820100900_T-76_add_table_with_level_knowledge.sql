-- liquibase formatted sql

-- changeset karmanov:20210820100900_T-76-1
create table knowledge_progress
(
    id bigint auto_increment,
    box_number int not null comment 'Номер коробки для которой указывается уровень знаний',
    type enum('direct', 'reverse', 'test', 'listening', 'spelling') not null comment 'Тип коробки',
    knowledge double not null comment 'Уровень знания, в от 0 до 1',
    constraint knowledge_progress_pk
        primary key (id)
) comment 'Связывает коробки и уровень знаний';

-- changeset karmanov:20210820100900_T-76-2
insert into knowledge_progress (box_number, type, knowledge) values (1, 'direct', 0.2);
insert into knowledge_progress (box_number, type, knowledge) values (2, 'direct', 0.4);
insert into knowledge_progress (box_number, type, knowledge) values (3, 'direct', 0.6);
insert into knowledge_progress (box_number, type, knowledge) values (4, 'direct', 0.7);
insert into knowledge_progress (box_number, type, knowledge) values (5, 'direct', 0.8);
insert into knowledge_progress (box_number, type, knowledge) values (6, 'direct', 0.9);
insert into knowledge_progress (box_number, type, knowledge) values (7, 'direct', 1.0);
