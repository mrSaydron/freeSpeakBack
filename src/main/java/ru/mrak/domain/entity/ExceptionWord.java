package ru.mrak.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import ru.mrak.domain.enumeration.TagEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "exception_word")
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExceptionWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "word")
    private String word;

    @NotNull
    @Column(name = "part_of_speech")
    @Enumerated(EnumType.STRING)
    private TagEnum partOfSpeech;

    @NotNull
    @Column(name = "target_word")
    private String targetWord;

    @NotNull
    @Column(name = "target_part_of_speech")
    @Enumerated(EnumType.STRING)
    private TagEnum targetPartOfSpeech;

}
