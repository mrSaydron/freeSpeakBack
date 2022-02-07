package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.service.UserWordService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface UserWordRepository extends JpaRepository<UserWord, Long>, JpaSpecificationExecutor<UserWord> {

    Optional<UserWord> findByUserAndWord(User user, Word word);
    List<UserWord> findAllByUserAndWordIn(User user, List<Word> words);

    void deleteAllByUserAndWord(User user, Word word);
    void deleteAllByUserAndWordIn(User user, List<Word> words);

    /**
     * Возвращает количество неудачных ответов за день
     */
    @Query(
        "select sum(uwp.failAttempts) " +
        "from UserWord uw " +
        "join uw.wordProgresses uwp " +
        "where uw.user = :user " +
        "and uwp.failLastDate >= :currentDay"
    )
    Optional<Integer> getCountFailAnswersByUserAndDate(User user, Instant currentDay);

    /**
     * Возвращает слова для изучения на указанный день
     */
    @Query(
        "select uw " +
        "from UserWord uw " +
        "join uw.wordProgresses uwp " +
        "where uw.user = :user " +
        "and uwp.boxNumber in :boxes " +
        "and (uwp.successfulLastDate < :currentDay or uwp.successfulLastDate is null)"
    )
    List<UserWord> findByAllByUserAndBoxesAndLessFailDateAndLessSuccessDate(
        User user,
        List<Integer> boxes,
        Instant currentDay
    );

    /**
     * Слова изученные сегодня
     */
    @Query(
        "select uw " +
            "from UserWord uw " +
            "join uw.wordProgresses uwp " +
            "where uw.user = :user " +
            "and uwp.successfulLastDate >= :greaterDay"
    )
    List<UserWord> findAllByUserAndGreaterThanSuccessDate(
        User user,
        Instant greaterDay
    );


    /**
     * Сбрасывает приоритет изучения слов для пользователя
     */
    @Modifying
    @Query(
        value = "update user_word " +
            "set priority = " + Long.MAX_VALUE + " " +
            "where user_id = :userId",
        nativeQuery = true
    )
    void resetPriority(@Param("userId") long userId);

    /**
     * Расставляет приоритеты изучения слов для указанного пользователя для книги
     */
    @Modifying
    @Query(
        value =
            "update user_word " +
            "set priority = ( " +
            "select coalesce(min(bshw.id), 9223372036854775807) priority " +
            "from book_sentence bs " +
            "join book_sentence_has_word bshw on bs.id = bshw.book_sentence_id " +
            "where bs.book_id = :bookId " +
            "and bshw.word_id is not null " +
            "and bshw.word_id = user_word.word_id " +
            "group by bshw.word_id) " +
            "where user_id = :userId and word_id in ( " +
            "select distinct bshwin.word_id " +
            "from book_sentence bsin " +
            "join book_sentence_has_word bshwin on bshwin.book_sentence_id = bsin.id " +
            "where bsin.book_id = :bookId)",
        nativeQuery = true
    )
    void updatePriorityByBook(@Param("userId") Long userId, @Param("bookId") long bookId);

    @Query(
        value =
            "select distinct uw.* " +
            "from user_word uw " +
            "join user_word_has_progress p on p.user_word_id = uw.id " +
            "where uw.user_id = :userId " +
            "and (p.box_number = " + UserWordService.PRELIMINARY_BOX_NUMBER + ") " +
            "and uw.word_id not in (:excludeWordIds)" +
            "order by uw.priority " +
            "limit :limit",
        nativeQuery = true
    )
    List<UserWord> getNextWords(
        @Param("userId") long userId,
        @Param("limit") int limit,
        @Param("excludeWordIds") List<Long> excludeWordIds
    );
}
