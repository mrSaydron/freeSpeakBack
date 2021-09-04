-- liquibase formatted sql

-- changeset karmanov:20210820100900_T-76-3
drop view book_user_know;
create view book_user_know as
select ujb.user_id                               AS user_id,
       ujb.book_id                               AS book_id,
       bd.id                                     AS book_dictionary_id,
       coalesce(sum(bdhw.count * know.knowledge) / wcnt.cnt, 0) AS know
from
    (select u.id AS user_id, b.id AS book_id from jhi_user u cross join book b) ujb
        left join user_dictionary ud on (ujb.user_id = ud.user_id)
        left join user_dictionary_has_word udhw on (ud.id = udhw.user_dictionary_id)
        left join user_word_progress user_prss on udhw.id = user_prss.user_dictionary_has_word_id
        left join knowledge_progress know on know.box_number = user_prss.box_number and know.type = user_prss.type
        join book_dictionary bd on (ujb.book_id = bd.book_id and (ud.target_language = bd.base_language or ud.target_language is null))
        left join book_dictionary_has_word bdhw on (bd.id = bdhw.book_dictionary_id and udhw.word_id = bdhw.word_id)

        join (
        select bd2.book_id AS book_id,
               bd2.base_language AS base_language,
               sum(w.count) AS cnt
        from book_dictionary bd2
                 join book_dictionary_has_word w on (bd2.id = w.book_dictionary_id)
        group by bd2.book_id, bd2.base_language) wcnt
             on (wcnt.book_id = ujb.book_id and wcnt.base_language = bd.base_language)
group by ujb.user_id, ujb.book_id, bd.id, wcnt.cnt;
