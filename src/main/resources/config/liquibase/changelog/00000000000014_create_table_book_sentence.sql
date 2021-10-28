-- liquibase formatted sql

-- changeset karmanov:00000000000014_create_table_book_sentence
create sequence book_sentence_seq;
create table book_sentence
(
    id bigint default nextval('public.book_sentence_seq') not null,
    book_id bigint not null,

    constraint book_sentence_pk
        primary key (id),
    constraint book_sentence_book_id_fk
        foreign key (book_id) references book (id)
            on delete cascade
);
alter sequence book_sentence_seq owned by book_sentence.id;

comment on table book_sentence is 'Предложение';

comment on column book_sentence.book_id is 'Идентификатор книги';
