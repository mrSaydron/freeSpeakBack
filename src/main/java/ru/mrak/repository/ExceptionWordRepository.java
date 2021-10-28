package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.ExceptionWord;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import java.util.Optional;

@Repository
public interface ExceptionWordRepository extends JpaRepository<ExceptionWord, Long> {
    Optional<ExceptionWord> findByWordAndPartOfSpeech(String word, PartOfSpeechEnum partOfSpeech);
}

