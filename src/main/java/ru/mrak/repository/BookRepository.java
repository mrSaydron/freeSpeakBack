package ru.mrak.repository;

import ru.mrak.domain.Book;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("select book from Book book where book.loadedUser.login = ?#{principal.username}")
    List<Book> findByLoadedUserIsCurrentUser();
}
