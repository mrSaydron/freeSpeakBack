package ru.mrak.repository;

import org.springframework.data.repository.query.Param;
import ru.mrak.domain.Word;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    Optional<Word> findByWord(String word);

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

    @Query(value = "update word " +
        "set word.total_amount = word.total_amount + (" +
        "select sum(bdhw.count) " +
        "from word w " +
        "join book_dictionary_has_word bdhw on w.id = bdhw.word_id " +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id " +
        " where bd.book_id = :bookId " +
        "and word.id = w.id) " +
        "where 1=1", nativeQuery = true)
    void updateTotalAmountByBookId(@Param("bookId") Long bookId);

    @Query(value = "select sum(bdhw.count) " +
        "from book_dictionary_has_word bdhw " +
        "join book_dictionary bd on bdhw.book_dictionary_id = bd.id " +
        "where bd.book_id = :bookId", nativeQuery = true)
    long getWordsCountByBook(@Param("bookId") Long bookId);
}
