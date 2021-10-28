-- liquibase formatted sql

-- changeset karmanov:00000000000015_create_table_book_sentences_has_word
create sequence book_sentence_has_word_seq;
create table book_sentence_has_word
(
    id bigint default nextval('public.book_sentence_has_word_seq') not null,
    book_sentence_id bigint not null,
    word_id bigint null,
    word varchar(255) not null,
    after_word varchar(16) not null,
    part_of_speech varchar(16) not null,

    constraint book_sentence_has_word_pk
        primary key (id),
    constraint book_sentence_has_word_book_sentence_id_fk
        foreign key (book_sentence_id) references book_sentence (id)
            on delete cascade,
    constraint book_sentence_has_word_word_id_fk
        foreign key (word_id) references word (id)
            on delete cascade
);
alter sequence book_sentence_has_word_seq owned by book_sentence_has_word.id;

comment on table book_sentence_has_word is 'Части книги';

comment on column book_sentence_has_word.book_sentence_id is 'Идентификатор предложения';
comment on column book_sentence_has_word.word_id is 'Идентификатор слова';
comment on column book_sentence_has_word.word is 'Слово в тексте';
comment on column book_sentence_has_word.after_word is 'Символы после слова';
comment on column book_sentence_has_word.part_of_speech is 'Часть речи слова в тексте';
