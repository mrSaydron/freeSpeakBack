-- liquibase formatted sql

-- changeset karmanov:T-31-1:20210404205300
alter table user_dictionary_has_word drop foreign key user_dictionary_has_word_user_dictionary_id_fk;
alter table user_dictionary modify id bigint auto_increment comment 'Идентификатор';
alter table user_dictionary_has_word
    add constraint user_dictionary_has_word_user_dictionary_id_fk
        foreign key (user_dictionary_id) references user_dictionary (id)
            on delete cascade;

-- changeset karmanov:T-31-2:20210404205300
alter table user_word_progress drop foreign key user_word_progress_user_dictionary_has_word_id_fk;
alter table user_dictionary_has_word modify id bigint auto_increment comment 'Идентификатор';
alter table user_word_progress
    add constraint user_word_progress_user_dictionary_has_word_id_fk
        foreign key (user_dictionary_has_word_id) references user_dictionary_has_word (id)
            on delete cascade;

-- changeset karmanov:T-31-3:20210404205300
alter table user_word_progress modify id bigint auto_increment comment 'Идентификатор';

