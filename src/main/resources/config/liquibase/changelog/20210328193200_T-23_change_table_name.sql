-- liquibase formatted sql

-- changeset karmanov:T-23-1:20210328193200
alter table dictionary rename to book_dictionary;

-- changeset karmanov:T-23-2:20210328193200
alter table dictionary_has_word rename to book_dictionary_has_word;

-- changeset karmanov:T-23-3:20210328193200
alter table book_dictionary rename column targer_language to target_language;

-- changeset karmanov:T-23-4:20210328193200
alter table book_dictionary_has_word rename column dictionary_id to book_dictionary_id;

-- changeset karmanov:T-23-5:20210328193200
alter table book rename column dictionary_id to book_dictionary_id;

-- changeset karmanov:T-23-6:20210328193200
alter table book_dictionary add book_id bigint null;
alter table book_dictionary add constraint book_dictionary_book_id_fk
    foreign key (book_id) references book (id) on delete cascade;

comment on column book_dictionary.book_id is 'идентификатор книги';

-- changeset karmanov:T-23-7:20210328193200
update book_dictionary
set book_id = book.id
from book
where book_dictionary.id = book.book_dictionary_id;

-- changeset karmanov:T-23-8:20210328193200
alter table book drop constraint fk_book_dictionary_id;
alter table book drop constraint ux_book_dictionary_id;

-- changeset karmanov:T-23-9:20210328193200
alter table book drop column book_dictionary_id;
