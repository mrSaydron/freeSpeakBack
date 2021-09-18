package ru.mrak.repository;

import org.springframework.data.repository.query.Param;
import ru.mrak.domain.Word;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    Optional<Word> findByWord(String word);

    Optional<Word> findByWordAndPartOfSpeech(String word, String partOfSpeech);

    @Modifying
    @Query(value = "update word " +
        "set word.total_amount = (" +
        "select sum(bdhw.count) " +
        "from word w " +
        "join book_dictionary_has_word bdhw on w.id = bdhw.word_id " +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id " +
        "join book b on bd.book_id = b.id " +
        "where b.public_book = true " +
        "and word.id = w.id) " +
        "where 1=1", nativeQuery = true)
    void updateTotalAmount();

    @Query(value = "select sum(bdhw.count)\n" +
        "from book_dictionary_has_word bdhw\n" +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id\n" +
        "join book b on bd.book_id = b.id\n" +
        "where b.public_book = true", nativeQuery = true)
    Long getWordsCount();

    @Modifying
    @Query(value = "update word w " +
        "set total_amount = coalesce(total_amount, 0) + ( " +
        "select COALESCE(sum(bdhw.count), 0) " +
        "from book_dictionary_has_word bdhw " +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id " +
        "where bd.book_id = 3 " +
        "and w.id = bdhw.word_id)", nativeQuery = true)
    void updateTotalAmountByBookId(@Param("bookId") Long bookId);

    @Query(value = "select sum(bdhw.count) " +
        "from book_dictionary_has_word bdhw " +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id " +
        "where bd.book_id = :bookId", nativeQuery = true)
    long getWordsCountByBook(@Param("bookId") Long bookId);

    List<Word> findAllByTranslateIsNull();

    @Query(value = "select * from word w where w.translate is null limit :count", nativeQuery = true)
    List<Word> findByTranslateIsNull(@Param("count") int count);

    List<Word> findByAudioFileRequestedIsFalse();
}
