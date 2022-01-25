package ru.mrak.repository;

import org.springframework.data.repository.query.Param;
import ru.mrak.model.entity.Word;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    Optional<Word> findByWord(String word);

    Optional<Word> findByWordAndPartOfSpeech(String word, PartOfSpeechEnum partOfSpeech);

    @Modifying
    @Query(value = "update word " +
        "set total_amount = (" +
        "select count(*) " +
        "from book_sentence_has_word bshw " +
        "where bshw.word_id = word.id) " +
        "where 1=1", nativeQuery = true)
    void updateTotalAmount();

    @Query(value = "select count(*) " +
        "from book_sentence_has_word bshw " +
        "where bshw.word_id is not null", nativeQuery = true)
    Long getWordsCount();

    @Modifying
    @Query(value = "update word " +
        "set total_amount = coalesce(word.total_amount, 0) + (" +
        "select count(*) " +
        "from book_sentence_has_word bshw " +
        "join book_sentence bs on bs.id = bshw.book_sentence_id " +
        "where bs.book_id = :bookId " +
        "and word.id = bshw.word_id) " +
        "where word.id in ( " +
        "select bshw_in.id " +
        "from book_sentence_has_word bshw_in " +
        "join book_sentence bs_in on bs_in.id = bshw_in.book_sentence_id " +
        "where bs_in.book_id = :bookId)", nativeQuery = true)
    void updateTotalAmountByBookId(@Param("bookId") Long bookId);

    @Query(value = "select count(*)\n" +
        "from book_sentence_has_word bshw\n" +
        "join book_sentence bs on bshw.book_sentence_id = bs.id\n" +
        "where bshw.word_id is not null\n" +
        "and bs.book_id = :bookId", nativeQuery = true)
    long getWordsCountByBook(@Param("bookId") Long bookId);

    List<Word> findAllByTranslateIsNull();

    @Query(value = "select * from word w where w.translate is null limit :count", nativeQuery = true)
    List<Word> findByTranslateIsNull(@Param("count") int count);

    List<Word> findByAudioFileRequestedIsFalse();

    /**
     * Ищет слово для теста словарного запаса вперед с указанным шагом от начала словаря
     * @param step - размер шага
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "where w.translate is not null " +
            "order by w.total_amount desc, w.id " +
            "offset :step " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findStepForwardForVocabulary(@Param("step") int step);

    /**
     /**
     * Ищет слово, для теста словарного запаса, вперед с указанным шагом от указанного слова
     * @param wordId - слово от которого ищем
     * @param step - размер шага
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "left join test_vocabulary_answer tva on w.id = tva.word_id and tva.test_vocabulary_id = :testVocabularyId " +
            "where tva.id is null " +
            "and w.translate is not null " +
            "and (w.total_amount < (select w_in.total_amount from word w_in where w_in.id = :wordId) " +
            "or (w.total_amount = (select w_in.total_amount from word w_in where w_in.id = :wordId) and w.id > :wordId)) " +
            "order by w.total_amount desc, w.id " +
            "offset :step " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findStepForwardForVocabulary(
        @Param("wordId") long wordId,
        @Param("step") int step,
        @Param("testVocabularyId") long testVocabularyId
    );

    /**
     * Ищет слово для теста словарного запаса назад с указанным шагом от указанного слова
     * @param wordId - слово от которого ищем
     * @param step - размер шага
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "left join test_vocabulary_answer tva on w.id = tva.word_id and tva.test_vocabulary_id = :testVocabularyId " +
            "where tva.id is null " +
            "and w.translate is not null " +
            "and (w.total_amount > (select w_in.total_amount from word w_in where w_in.id = :wordId) " +
            "or (w.total_amount = (select w_in.total_amount from word w_in where w_in.id = :wordId) and w.id < :wordId)) " +
            "order by w.total_amount, w.id desc " +
            "offset :step " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findStepBackForVocabulary(
        @Param("wordId") long wordId,
        @Param("step") int step,
        @Param("testVocabularyId") long testVocabularyId
    );

    /**
     * Ищет последнее слово для теста словарного запаса назад
     * @param wordId - слово от которого ищем
     * @param step - размер шага
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "left join test_vocabulary_answer tva on w.id = tva.word_id and tva.test_vocabulary_id = :testVocabularyId " +
            "where tva.id is null " +
            "and w.translate is not null " +
            "and (w.total_amount > (select w_in.total_amount from word w_in where w_in.id = :wordId) " +
            "or (w.total_amount = (select w_in.total_amount from word w_in where w_in.id = :wordId) and w.id < :wordId)) " +
            "order by w.total_amount desc, w.id " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findLastBackForVocabulary(
        @Param("wordId") long wordId,
        @Param("testVocabularyId") long testVocabularyId
    );

    /**
     * Ищет следующее слово для теста словарного запаса
     * @param wordId - слово от которого ищем
     * @param step - размер шага
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "left join test_vocabulary_answer tva on w.id = tva.word_id and tva.test_vocabulary_id = :testVocabularyId " +
            "where tva.id is null " +
            "and w.translate is not null " +
            "and (w.total_amount < (select w_in.total_amount from word w_in where w_in.id = :wordId) " +
            "or (w.total_amount = (select w_in.total_amount from word w_in where w_in.id = :wordId) and w.id > :wordId)) " +
            "order by w.total_amount desc, w.id " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findNextForVocabulary(
        @Param("wordId") long wordId,
        @Param("testVocabularyId") long testVocabularyId
    );

    /**
     * Сколько слов знает пользователь
     */
    @Query(
        value =
            "select count(*) " +
            "from word w " +
            "where w.total_amount >= :totalAmount " +
            "and w.translate is not null",
        nativeQuery = true
    )
    int countResultWordsForTestVocabulary(@Param("totalAmount") long totalAmount);

    /**
     * Возвращает граничное слово по результатам тестирования словарного запаса
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "where w.total_amount >= :totalAmount " +
            "and w.translate is not null " +
            "order by w.total_amount, w.id desc " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findEdgeWordForTestVocabulary(long totalAmount);

    /**
     * Возвращает слова по результатм тестирования словарного запаса. Исключаются слова которые уже находятся на изучении
     */
    @Query(
        value =
            "select distinct w.* " +
            "from word w " +
            "left join user_word uw on w.id = uw.word_id and uw.user_id = :userId " +
            "left join user_word_has_progress uwhp on uw.id = uwhp.user_word_id " +
            "where w.total_amount >= :totalAmount " +
            "and (uw.id is null or uwhp.box_number = 0) " +
            "and w.translate is not null",
        nativeQuery = true
    )
    List<Word> findResultWordsForTestVocabulary(
        @Param("totalAmount") long totalAmount,
        @Param("userId") long userId
    );

    /**
     * Ищет последнее слово для теста словарного запаса вперед
     * @param wordId - слово от которого ищем
     */
    @Query(
        value =
            "select w.* " +
            "from word w " +
            "left join test_vocabulary_answer tva on w.id = tva.word_id and tva.test_vocabulary_id = :testVocabularyId " +
            "where tva.id is null " +
            "and w.translate is not null " +
            "and (w.total_amount < (select w_in.total_amount from word w_in where w_in.id = :wordId) " +
            "or (w.total_amount = (select w_in.total_amount from word w_in where w_in.id = :wordId) and w.id > :wordId)) " +
            "order by w.total_amount, w.id desc " +
            "limit 1",
        nativeQuery = true
    )
    Optional<Word> findLastForwardForVocabulary(
        @Param("wordId") long wordId,
        @Param("testVocabularyId") long testVocabularyId
    );

    int countAllByTranslateNotNull();

    Optional<Word> findFirstByTranslateIsNotNullOrderByTotalAmountDesc();
}
