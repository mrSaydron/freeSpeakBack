package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.testVocabulary.TestVocabulary;
import ru.mrak.model.entity.testVocabulary.TestVocabularyAnswer;

import java.util.List;

public interface TestVocabularyAnswerRepository extends JpaRepository<TestVocabularyAnswer, Long> {

    int countAllByTestVocabularyAndSuccessIsFalse(TestVocabulary testVocabulary);

    List<TestVocabularyAnswer> findAllByTestVocabularyAndSuccessIsFalse(TestVocabulary testVocabulary);

}
