-- liquibase formatted sql

-- changeset karmanov:2022.01.24_add_indexes
create index book_sentence_book_id_index on book_sentence (book_id);
create index book_sentence_has_word_book_sentence_id_index on book_sentence_has_word (book_sentence_id);
create index book_sentence_has_word_word_id_index on book_sentence_has_word (word_id);
