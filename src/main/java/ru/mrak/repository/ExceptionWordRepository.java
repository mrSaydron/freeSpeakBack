package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.entity.ExceptionWord;
import ru.mrak.domain.enumeration.TagEnum;

import java.util.Optional;

@Repository
public interface ExceptionWordRepository extends JpaRepository<ExceptionWord, Long> {
    Optional<ExceptionWord> findByWordAndPartOfSpeech(String word, TagEnum partOfSpeech);
}

