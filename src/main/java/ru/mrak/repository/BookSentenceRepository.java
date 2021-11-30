package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface BookSentenceRepository extends JpaRepository<BookSentence, Long> {

    List<BookSentence> findAllByBookId(Long bookId);

    /**
     * Находит все предложения из всех книг. Предложения берутся только те, в которые состоят из слов пользователя с прогрессом
     * больше указанной коробки и должны иметь в наличии слова из wordIds. Так же эти предложения не должны быть изученны пользователем
     * сегодня
     * @param userId - идентификатор пользователя
     * @param boxNumber - слова в предложении больше данной коробки
     * @param wordIds - слова которые должны быть в искомом предложении
     * @param day - текущий день, отфильтровывает предложения которые уже были показанны сегодня
     * @param size - ограничение на количество найденных предложений
     */
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

    /**
     * Возвращает все предложения из выбранной книги, которые состоят из слов с прогрессом больше или равном указанному
     * @param userId - идентификатор пользователя
     * @param bookId - идентификатор книги
     * @param boxNumber - номер коробки
     * @param sentenceId - номер коробки
     */
    @Query(
        value =
            "select distinct bs.* " +
            "from book_sentence bs " +
            "join book_sentence_has_word bw on bs.id = bw.book_sentence_id " +
            "where bs.id not in ( " +
            "    select distinct bw_in.book_sentence_id " +
            "    from book_sentence bs_in " +
            "             join book_sentence_has_word bw_in on bw_in.book_sentence_id = bs_in.id " +
            "             join word w_in on w_in.id = bw_in.word_id " +
            "             left join user_word uw_in on bw_in.word_id = uw_in.word_id and uw_in.user_id = :userId " +
            "             left join user_word_has_progress uwp on uw_in.id = uwp.user_word_id and uwp.box_number >= :boxNumber " +
            "    where bs_in.book_id = :bookId " +
            "    and uwp.id is null " +
            "    and w_in.translate is not null" +
            "    ) " +
            "and bs.book_id = :bookId " +
            "and bs.id > :sentenceId",
        nativeQuery = true
    )
    List<BookSentence> findAllWhereAllLearnedUserWords(
        @Param("userId") long userId,
        @Param("bookId") long bookId,
        @Param("boxNumber") int boxNumber,
        @Param("sentenceId") long sentenceId
    );

    Optional<BookSentence> findFirstByBook(Book book);

    /**
     * Проверяет есть ли все слова пользователя из указанной коробки (и больше) в указанном предложении
     */
    @Query(
        value =
            "select count(*) = 0 " +
            "from book_sentence_has_word bw " +
            "         left join user_word uw on bw.word_id = uw.word_id and uw.user_id = :userId " +
            "         left join user_word_has_progress uwhp on uw.id = uwhp.user_word_id and uwhp.box_number >= :boxNumber " +
            "join word w on bw.word_id = w.id " +
            "where (uw.id is null or uwhp.id is null) " +
            "and bw.book_sentence_id = :sentenceId " +
            "and w.translate is not null",
        nativeQuery = true
    )
    boolean hasAllUserWordInSentence(
        @Param("userId") long userId,
        @Param("sentenceId") long sentenceId,
        @Param("boxNumber") int boxNumber
    );
}
