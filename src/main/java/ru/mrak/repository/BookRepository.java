package ru.mrak.repository;

import ru.mrak.model.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = "select distinct book from Book book left join fetch book.users",
        countQuery = "select count(distinct book) from Book book")
    Page<Book> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct book from Book book left join fetch book.users")
    List<Book> findAllWithEagerRelationships();

    @Query("select book from Book book left join fetch book.users where book.id =:id")
    Optional<Book> findOneWithEagerRelationships(@Param("id") Long id);

    /**
     * Возвращает идентификаторы слов из книги, которых нет в словаре пользователя
     */
    @Query(value = "select w.id " +
        "from book_sentence bs " +
        "join book_sentence_has_word bshw on bs.id = bshw.book_sentence_id " +
        "join word w on w.id = bshw.word_id " +
        "left join user_word_progress uwp on uwp.word_id = bshw.word_id and uwp.user_id = :userId " +
        "where bs.book_id = :bookId " +
        "and w.translate is not null " +
        "and uwp.word_id is null", nativeQuery = true)
    List<Long> getMissingWords(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
