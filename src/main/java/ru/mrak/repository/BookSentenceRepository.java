package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.BookSentence;

import java.util.List;

public interface BookSentenceRepository extends JpaRepository<BookSentence, Long> {
    List<BookSentence> findAllByBookId(Long bookId);
}
