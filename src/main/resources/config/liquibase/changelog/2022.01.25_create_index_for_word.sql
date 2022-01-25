-- liquibase formatted sql

-- changeset karmanov:2022.01.25_add_index_for_word
create unique index word_word_part_of_speech_uindex
    on word (word, part_of_speech);
