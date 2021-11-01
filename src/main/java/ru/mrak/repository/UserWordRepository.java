package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.model.entity.userWordProgress.UserWordHasProgress;

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
     * @param user
     * @param currentDay
     * @return
     */
    @Query(
        "select count(uwp) " +
        "from UserWord uw " +
        "join uw.userWordHasProgresses uwp " +
        "where uw.user = :userId " +
        "and uwp.failLastDate >= :currentDay"
    )
    Integer getCountFailAnswersByUserAndDate(User user, Instant currentDay);

    /**
     * Возвращает слова для изучения на указанный день
     * @param user
     * @param boxes
     * @param currentDay
     * @return
     */
    @Query(
        "select uw " +
        "from UserWord uw " +
        "join uw.userWordHasProgresses uwp " +
        "where uw.user = :user " +
        "and uwp.boxNumber in :boxes " +
        "and (uwp.successfulLastDate < :currentDay or uwp.successfulLastDate is null) " +
        "and (uwp.failLastDate < :currentDay or uwp.failLastDate is null)"
    )
    List<UserWord> getByUserAndBoxesAndLessFailDateAndLessSuccessDate(
        User user,
        List<Integer> boxes,
        Instant currentDay
    );
}
