-- liquibase formatted sql

-- changeset karmanov:00000000000020_create_table_user_has_sentences
create table user_has_sentences
(
    user_id bigint not null,
    book_sentence_id bigint not null,
    successful_last_date timestamp,
    fail_last_date timestamp,

    constraint user_has_sentences_pk
        primary key (user_id, book_sentence_id),
    constraint user_has_sentences_book_user_id_fk
        foreign key (user_id) references jhi_user (id)
            on delete cascade,
    constraint user_has_sentences_sentence_id_fk
        foreign key (book_sentence_id) references book_sentence (id)
            on delete cascade
);

comment on table user_has_sentences is 'Связка пользователя и предложений из книги';

comment on column user_has_sentences.user_id is 'Идентификатор пользователя';
comment on column user_has_sentences.book_sentence_id is 'Идентификатор предложения';
comment on column user_has_sentences.successful_last_date is 'Дата последнего успешного перевода';
comment on column user_has_sentences.fail_last_date is 'Дата последнего не успешного перевода';
