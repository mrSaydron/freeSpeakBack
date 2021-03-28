package ru.mrak.repository;

import ru.mrak.domain.BookDictionaryHasWord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DictionaryHasWord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryHasWordRepository extends JpaRepository<BookDictionaryHasWord, Long> {
}
