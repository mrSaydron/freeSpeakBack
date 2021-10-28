-- liquibase formatted sql

-- changeset karmanov:00000000000002_create_table_book_user
create table book_user
(
    user_id bigint not null,
    book_id bigint not null,

    constraint book_user_pk
        primary key (user_id, book_id),
    constraint book_user_jhi_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade,
    constraint book_user_book_id_fk
        foreign key (user_id) references book (id)
            on delete cascade
);
comment on table book_user is 'Связывает пользователя и книги';

comment on column book_user.user_id is 'Идентификатор пользователя';
comment on column book_user.book_id is 'Идентификатор книги';
