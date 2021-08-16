package ru.mrak.repository;

import ru.mrak.domain.Book;

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

    @Query("select book from Book book where book.loadedUser.login = ?#{principal.username}")
    List<Book> findByLoadedUserIsCurrentUser();

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
    @Query(value = "select bdhw.word_id " +
        "from book_dictionary bd " +
        "join book_dictionary_has_word bdhw on bd.id = bdhw.book_dictionary_id " +
        "left join user_dictionary_has_word udhw on udhw.word_id = bdhw.word_id " +
        "left join user_dictionary ud on udhw.user_dictionary_id = ud.id " +
        "    and bd.base_language = ud.base_language " +
        "    and bd.target_language = ud.target_language " +
        "    and ud.user_id = :userId " +
        "where bd.book_id = :bookId " +
        "and udhw.id is null", nativeQuery = true)
    List<Long> getMissingWords(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
