package ru.mrak.model.entity.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Хранит попытки тестирования словарного запасу у пользователя
 */
@Entity
@Table(name = "test_vocabulary")
@Getter
@Setter
@Accessors(chain = true)
public class TestVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_vocabulary_seq")
    @SequenceGenerator(name = "test_vocabulary_seq", sequenceName = "test_vocabulary_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "try_number")
    private int tryNumber;

    @NotNull
    @Column(name = "try_date")
    private Instant tryDate;

    @Column(name = "result_count")
    private Integer resultCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_word_id")
    private Word resultWord;

}
