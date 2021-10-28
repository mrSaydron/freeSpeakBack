package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.userWordProgress.UserWordProgress;
import ru.mrak.model.entity.userWordProgress.UserWordProgressId;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, UserWordProgressId>, JpaSpecificationExecutor<UserWordProgress> {

    List<UserWordProgress> findAllByUserIdAndWordId(Long userId, Long wordId);
    List<UserWordProgress> findAllByUserIdAndWordIdIn(Long userId, List<Long> wordIds);

    void deleteAllByUserIdAndWordId(Long userId, Long wordId);
    void deleteAllByUserIdAndWordIdIn(Long userId, List<Long> wordIds);

    /**
     * Возвращает количество неудаяных ответов за день
     * @param userId
     * @param currentDay
     * @return
     */
    @Query(
        "select count(uw) " +
            "from UserWordProgress uw " +
            "where uw.userId = :userId " +
            "and uw.failLastDate >= :currentDay")
    Integer getCountFailAnswersByUserIdAndDate(Long userId, Instant currentDay);

    /**
     * Возвращает слова для изучения на указанный день
     * @param user
     * @param boxes
     * @param currentDay
     * @return
     */
    @Query(
        "select uw " +
            "from UserWordProgress uw " +
            "where uw.userId = :userId " +
            "and uw.boxNumber in :boxes " +
            "and (uw.successfulLastDate < :currentDay or uw.successfulLastDate is null) " +
            "and (uw.failLastDate < :currentDay or uw.failLastDate is null)"
    )
    List<UserWordProgress> getByUserIdAndBoxesAndLessFailDateAndLessSuccessDate(User user, List<Integer> boxes, Instant currentDay);
}
