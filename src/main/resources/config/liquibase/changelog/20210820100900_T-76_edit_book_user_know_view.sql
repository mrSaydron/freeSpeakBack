-- liquibase formatted sql

-- changeset karmanov:20210820100900_T-76-3
create or replace view book_user_know as
select ujb.user_id                               AS user_id,
       ujb.book_id                               AS book_id,
       bd.id                                     AS book_dictionary_id,
       ifnull(sum(bdhw.count * know.knowledge) / wcnt.cnt, 0) AS know
from (select u.id AS user_id, b.id AS book_id from lib_four.jhi_user u join lib_four.book b) ujb
         left join lib_four.user_dictionary ud on (ujb.user_id = ud.user_id)
         left join lib_four.user_dictionary_has_word udhw on (ud.id = udhw.user_dictionary_id)
         left join lib_four.user_word_progress user_prss on udhw.id = user_prss.user_dictionary_has_word_id
         left join lib_four.knowledge_progress know on know.box_number = user_prss.box_number and know.type = user_prss.type
         join lib_four.book_dictionary bd on (ujb.book_id = bd.book_id and (ud.target_language = bd.base_language or ud.target_language is null))
         left join lib_four.book_dictionary_has_word bdhw on (bd.id = bdhw.book_dictionary_id and udhw.word_id = bdhw.word_id)

         join (
    select bd2.book_id AS book_id,
           bd2.base_language AS base_language,
           sum(w.count) AS cnt
    from lib_four.book_dictionary bd2
             join lib_four.book_dictionary_has_word w on (bd2.id = w.book_dictionary_id)
    group by bd2.book_id, bd2.base_language) wcnt
              on (wcnt.book_id = ujb.book_id and wcnt.base_language = bd.base_language)
group by ujb.user_id, ujb.book_id;
