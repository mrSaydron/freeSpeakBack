-- liquibase formatted sql

-- changeset karmanov:T-47-1:20210501231400
create view book_user_know as
select ujb.user_id, ujb.book_id, bd.id as book_dictionary_id, coalesce(sum(bdhw.count) / wcnt.cnt, 0) as know
from (
         select u.id user_id, b.id book_id
         from jhi_user u, book b
     ) as ujb
         left join user_dictionary ud on ujb.user_id = ud.user_id
         left join user_dictionary_has_word udhw on ud.id = udhw.user_dictionary_id
         join book_dictionary bd on ujb.book_id = bd.book_id and (ud.target_language = bd.base_language or ud.target_language is null)
         left join book_dictionary_has_word bdhw on bd.id = bdhw.book_dictionary_id and udhw.word_id = bdhw.word_id
         join (
    select bd2.book_id, bd2.base_language, sum(w.count) as cnt
    from book_dictionary bd2
             join book_dictionary_has_word w on bd2.id = w.book_dictionary_id
    group by bd2.book_id, bd2.base_language
) as wcnt on wcnt.book_id = ujb.book_id and wcnt.base_language = bd.base_language
group by ujb.user_id, ujb.book_id, bd.id, wcnt.cnt;
