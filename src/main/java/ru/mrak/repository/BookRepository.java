package ru.mrak.repository;

import ru.mrak.model.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.User;

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
        "from BookSentence bs " +
        "join bs.words bshw " +
        "join Word w on w = bshw.translate " +
        "left join UserWord uw on uw.word = bshw.translate and uw.user = :user " +
        "where bs.book = :book " +
        "and w.translate is not null " +
        "and uw.word is null")
    List<Long> getMissingWords(@Param("user") User user, @Param("book") Book book);

}
