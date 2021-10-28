-- liquibase formatted sql

-- changeset karmanov:00000000000001_create_table_book
create sequence book_seq;
create table book
(
    id bigint default nextval('public.book_seq') not null,
    title varchar(255) not null,
    author varchar(255) null,
    picture_id varchar(255) null,

    constraint book_pk
        primary key (id)
);
alter sequence book_seq owned by book.id;

comment on column book.title is 'Название книга';
comment on column book.author is 'АВтор книги';
comment on column book.picture_id is 'Идентификатор картинки для книги';

comment on table book is 'Книга';
