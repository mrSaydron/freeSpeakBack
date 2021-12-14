-- liquibase formatted sql

-- changeset karmanov:00000000000016_create_view_book_user_know
create view book_user_know as
SELECT ujb.user_id,
       ujb.book_id,
       COALESCE(sum(know.knowledge) / wcnt.cnt * 100, 0) AS know
FROM (SELECT u.id AS user_id, b.id AS book_id FROM jhi_user u CROSS JOIN book b) ujb
LEFT JOIN book_sentence bp ON bp.book_id = ujb.book_id
LEFT JOIN book_sentence_has_word bw ON bw.book_sentence_id = bp.id
LEFT join word w2 on bw.word_id = w2.id and w2.translate is not null
LEFT JOIN user_word uw ON uw.user_id = ujb.user_id and uw.word_id = bw.word_id
LEFT JOIN user_word_has_progress user_prss ON user_prss.user_word_id = uw.id
LEFT JOIN knowledge_progress know ON know.box_number = user_prss.box_number
LEFT JOIN (SELECT bp2.book_id, count(*) AS cnt
           FROM book_sentence bp2
                    JOIN book_sentence_has_word bw2 ON bw2.book_sentence_id = bp2.id AND bw2.word_id IS NOT NULL
                    join word w on bw2.word_id = w.id
           where w.translate is not null
           GROUP BY bp2.book_id) wcnt ON wcnt.book_id = ujb.book_id
GROUP BY ujb.user_id, ujb.book_id, wcnt.cnt;
