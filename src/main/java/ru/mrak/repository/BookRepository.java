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
        "left join ( " +
        "select udhw.id, udhw.word_id, ud.base_language, ud.target_language " +
        "from user_dictionary ud " +
        "join user_dictionary_has_word udhw on ud.id = udhw.user_dictionary_id " +
        "where ud.user_id = :userId " +
        ") user_words on user_words.word_id = bdhw.word_id " +
        "where user_words.id is null " +
        "and bd.book_id = :bookId", nativeQuery = true)
    List<Long> getMissingWords(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
