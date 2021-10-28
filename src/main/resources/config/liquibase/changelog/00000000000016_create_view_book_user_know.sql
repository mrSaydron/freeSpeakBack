-- liquibase formatted sql

-- changeset karmanov:00000000000016_create_view_book_user_know
create view book_user_know as
select ujb.user_id                               AS user_id,
       ujb.book_id                               AS book_id,
       coalesce(sum(know.knowledge) / wcnt.cnt, 0) AS know
from
    (select u.id AS user_id, b.id AS book_id from jhi_user u cross join book b) ujb

    left join user_word_progress user_prss on ujb.user_id = user_prss.user_id
    left join knowledge_progress know on know.box_number = user_prss.box_number and know.type = user_prss.type

    join book_sentence bp on bp.book_id = ujb.book_id
    join book_sentence_has_word bw on bw.book_sentence_id = bp.id and bw.word_id is not null and user_prss.word_id = bw.word_id

    join (
        select bp2.book_id AS book_id, count(*) AS cnt
        from book_sentence bp2
        join book_sentence_has_word bw2 on bw2.book_sentence_id = bp2.id and bw2.word_id is not null
        group by bp2.book_id) wcnt on wcnt.book_id = ujb.book_id
group by ujb.user_id, ujb.book_id, wcnt.cnt;
