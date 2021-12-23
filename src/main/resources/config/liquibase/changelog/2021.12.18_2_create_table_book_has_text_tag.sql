-- liquibase formatted sql

-- changeset karmanov:2021.12.18_2_create_table_book_has_text_tag
create table book_has_text_tag
(
    book_id bigint not null,
    text_tag_id bigint not null,

    constraint book_has_text_tag_pk primary key (book_id, text_tag_id),
    constraint book_has_text_tag_book_id
        foreign key (book_id) references book (id)
            on delete cascade,
    constraint book_has_text_tag_text_tag_id
        foreign key (text_tag_id) references text_tag (id)
            on delete cascade

);
comment on table book_has_text_tag is 'Связка книг и тегов';

comment on column book_has_text_tag.book_id is 'Идентификатор книги';
comment on column book_has_text_tag.text_tag_id is 'Идентификатор тега';
