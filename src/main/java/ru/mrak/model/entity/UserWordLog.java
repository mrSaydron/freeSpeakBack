package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.converter.UserWordLogTypeEnumConverter;
import ru.mrak.model.entity.testVocabulary.TestVocabulary;
import ru.mrak.model.enumeration.UserWordLogTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "user_word_log")
@Getter
@Setter
public class UserWordLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_word_log_seq")
    @SequenceGenerator(name = "user_word_log_seq", sequenceName = "user_word_log_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Convert(converter = UserWordLogTypeEnumConverter.class)
    @Column(name = "type_id")
    private UserWordLogTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_vocabulary_id")
    private TestVocabulary testVocabulary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    @NotNull
    private Word word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "date")
    @NotNull
    private Instant date;
}
