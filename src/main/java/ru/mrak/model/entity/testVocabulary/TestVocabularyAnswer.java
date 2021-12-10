package ru.mrak.model.entity.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.entity.Word;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Результат ответов пользователя в тесте на словарный запас
 */
@Entity
@Table(name = "test_vocabulary_answer")
@Getter
@Setter
@Accessors(chain = true)
public class TestVocabularyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_vocabulary_answer_seq")
    @SequenceGenerator(name = "test_vocabulary_answer_seq", sequenceName = "test_vocabulary_answer_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_vocabulary_id")
    private TestVocabulary testVocabulary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @Column(name = "success")
    private boolean success;

}
