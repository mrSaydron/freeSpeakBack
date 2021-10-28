package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.bookUserKnow.BookUserKnow;
import ru.mrak.model.entity.User;

import java.util.Optional;

public interface BookUserKnowRepository extends JpaRepository<BookUserKnow, Long>, JpaSpecificationExecutor<BookUserKnow> {

    Optional<BookUserKnow> findByUserAndBook(User user, Book entity);
}
