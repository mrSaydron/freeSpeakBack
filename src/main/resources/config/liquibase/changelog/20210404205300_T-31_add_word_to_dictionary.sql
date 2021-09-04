-- liquibase formatted sql

-- changeset karmanov:T-31-1:20210404205300
alter table user_dictionary_has_word drop constraint user_dictionary_has_word_user_dictionary_id_fk;

create sequence user_dictionary_seq;
alter table user_dictionary alter column id set default nextval('public.user_dictionary_seq');
alter sequence user_dictionary_seq owned by user_dictionary.id;

alter table user_dictionary_has_word
    add constraint user_dictionary_has_word_user_dictionary_id_fk
        foreign key (user_dictionary_id) references user_dictionary (id)
            on delete cascade;

-- changeset karmanov:T-31-2:20210404205300
alter table user_word_progress drop constraint user_word_progress_user_dictionary_has_word_id_fk;

create sequence user_dictionary_has_word_seq;
alter table user_dictionary_has_word alter column id set default nextval('public.user_dictionary_has_word_seq');
alter sequence user_dictionary_has_word_seq owned by user_dictionary_has_word.id;

alter table user_word_progress
    add constraint user_word_progress_user_dictionary_has_word_id_fk
        foreign key (user_dictionary_has_word_id) references user_dictionary_has_word (id)
            on delete cascade;

-- changeset karmanov:T-31-3:20210404205300
create sequence user_word_progress_seq;
alter table user_word_progress alter column id set default nextval('public.user_word_progress_seq');
alter sequence user_word_progress_seq owned by user_word_progress.id;
