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
        "where 1=1", nativeQuery = true)
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
}
