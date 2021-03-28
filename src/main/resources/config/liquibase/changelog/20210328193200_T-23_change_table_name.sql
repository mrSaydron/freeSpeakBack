-- liquibase formatted sql

-- changeset karmanov:T-23-1:20210328193200
rename table dictionary to book_dictionary;

-- changeset karmanov:T-23-2:20210328193200
rename table dictionary_has_word to book_dictionary_has_word;

-- changeset karmanov:T-23-3:20210328193200
alter table book_dictionary change targer_language target_language varchar(255) null;

-- changeset karmanov:T-23-4:20210328193200
alter table book_dictionary_has_word change dictionary_id book_dictionary_id bigint null;

-- changeset karmanov:T-23-5:20210328193200
alter table book change dictionary_id book_dictionary_id bigint null;

-- changeset karmanov:T-23-6:20210328193200
alter table book_dictionary add book_id bigint null comment 'идентификатор книги';
alter table book_dictionary add constraint book_dictionary_book_id_fk
    foreign key (book_id) references book (id) on delete cascade;

-- changeset karmanov:T-23-7:20210328193200
update book_dictionary, book
set book_dictionary.book_id = book.id
where book_dictionary.id = book.book_dictionary_id;

-- changeset karmanov:T-23-8:20210328193200
alter table book drop foreign key fk_book_dictionary_id;
alter table book drop key ux_book_dictionary_id;

-- changeset karmanov:T-23-9:20210328193200
alter table book drop column book_dictionary_id;

-- changeset karmanov:T-23-10:20210328193200
alter table book_dictionary modify book_id bigint not null comment 'идентификатор книги';
