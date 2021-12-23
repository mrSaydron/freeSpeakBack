-- liquibase formatted sql

-- changeset karmanov:2021.12.18_1_create_table_text_tag
create sequence text_tag_seq;
create table text_tag
(
    id bigint default nextval('public.text_tag_seq'),
    tag_name varchar(255) not null,
    type_name varchar(255) not null,
    text_tag_parent_id bigint null,

    constraint text_tag_pk primary key (id)
);
alter sequence text_tag_seq owned by text_tag.id;
comment on table text_tag is 'Теги на тескты';

comment on column text_tag.tag_name is 'Наименование тега';
comment on column text_tag.type_name is 'Тип тега';
comment on column text_tag.text_tag_parent_id is 'Идентификатор связанного тега';

insert into text_tag (tag_name, type_name, text_tag_parent_id) VALUES
('Детектив', 'genre', null),
('Криминальный детектив', 'genre', 1),
('Светский детектив', 'genre', 1),
('Исторический детектив', 'genre', 1),
('Фантастика', 'genre', null),
('Научная фантастика', 'genre', 5),
('Фентези', 'genre',5),
('Приключения', 'genre', null),
('Роман', 'genre', null),
('Роман исторический', 'genre', 9),
('Роман любовный', 'genre', 9),
('Роман приключенческий', 'genre', 9),
('Научная книга', 'genre', null),
('Техническая литература', 'genre', null),
('Статья', 'text type', null),
('Книга', 'text type', null),
('Фольклор', 'genre', null),
('Эпос', 'genre', 17),
('Легенды', 'genre', 17),
('Сказки', 'genre', 17),
('Юмор', 'genre', null),
('Анекдоты', 'genre', 21),
('Поэзия', 'text format', null),
('Проза', 'text format', null),
('Детская книга', 'genre', null),
('Документальная литература', 'genre', null),
('Биография', 'genre', 26),
('Публицистика', 'genre', 26),
('Военное дело', 'genre', null),
('Деловая литература', 'genre', null),
('Бизнес', 'genre', 30),
('Политика', 'genre', 30),
('Финансы', 'genre', 30),
('Экономика', 'genre', 30),
('Религиозная литература', 'genre', null),
('Учебная книга', 'genre', null),
('Методическое пособие', 'genre', 36),
('Учебник', 'genre', 36),
('Психология', 'genre', 36),
('Справочник', 'genre', 14),
('Туризм', 'genre', null),
('Домашние животные', 'genre', null),
('Домоводство', 'genre', null),
('Зарубежная литература', 'text format', null),
('Русская литература', 'text format', null);


