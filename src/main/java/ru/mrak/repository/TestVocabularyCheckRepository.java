package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.testVocabulary.TestVocabularyCheck;

public interface TestVocabularyCheckRepository extends JpaRepository<TestVocabularyCheck, Long> {
}
