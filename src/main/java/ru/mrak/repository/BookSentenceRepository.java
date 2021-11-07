package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;

import java.time.Instant;
import java.util.List;

public interface BookSentenceRepository extends JpaRepository<BookSentence, Long> {

    List<BookSentence> findAllByBookId(Long bookId);

    @Query(value = "select distinct bs.* " +
        "from book_sentence bs " +
        "join book_sentence_has_word bw on bs.id = bw.book_sentence_id " +
        "left join user_has_sentences us on bs.id = us.book_sentence_id and us.user_id = :userId " +
        "where bs.id not in ( " +
        "    select distinct bw_in.book_sentence_id " +
        "    from book_sentence_has_word bw_in " +
        "             left join user_word uw_in on bw_in.word_id = uw_in.word_id and uw_in.user_id = :userId " +
        "             left join user_word_has_progress uwp on uw_in.id = uwp.user_word_id " +
        "    where (uw_in.id is null or (uwp.box_number < :boxNumber and uw_in.word_id not in :words)) " +
        "      and bw_in.word_id is not null " +
        "    ) " +
        "and bw.word_id in :words " +
        "and (us.user_id is null or (us.successful_last_date is null and (us.fail_last_date is null or us.fail_last_date < :day))) " +
        "limit :size", nativeQuery = true)
    List<BookSentence> findAllForLearnByUserId(
        @Param("userId") long userId,
        @Param("boxNumber") long boxNumber,
        @Param("words") List<Long> wordIds,
        @Param("day") Instant day,
        @Param("size") int size
    );

}
