package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.testVocabulary.TestVocabulary;

import java.util.Optional;

public interface TestVocabularyRepository extends JpaRepository<TestVocabulary, Long> {

    Optional<TestVocabulary> findFirstByUserOrderByTryNumberDesc(User user);

}
