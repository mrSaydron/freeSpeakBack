-- liquibase formatted sql

-- changeset karmanov:00000000000030_add_columns_to_book_user
alter table book_user add is_reading boolean default false not null;
comment on column book_user.is_reading is 'Книга для изучения';

alter table book_user add last_read_sentence_id bigint null;
alter table book_user add constraint book_user_last_read_sentence_id_book_sentence_id_fk
    foreign key (last_read_sentence_id) references book_sentence on delete cascade;
comment on column book_user.last_read_sentence_id is 'Идентификатор последнего изученного предложения';

alter table book_user add bookmark_sentence_id bigint null;
alter table book_user add constraint book_user_bookmark_sentence_id_book_sentence_id_fk
    foreign key (bookmark_sentence_id) references book_sentence on delete cascade;
comment on column book_user.bookmark_sentence_id is 'Идентификатор предложения для автоматической закладки';



