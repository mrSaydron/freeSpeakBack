package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mrak.domain.Book;
import ru.mrak.domain.BookUserKnow;
import ru.mrak.domain.User;

import java.util.Optional;

public interface BookUserKnowRepository extends JpaRepository<BookUserKnow, Long>, JpaSpecificationExecutor<BookUserKnow> {

    Optional<BookUserKnow> findByUserAndBookAndBookDictionaryId(User user, Book book, Long dictionaryId);

}
