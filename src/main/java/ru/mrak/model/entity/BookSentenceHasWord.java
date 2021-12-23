package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import javax.persistence.*;

// book_sentence_has_word

@Getter
@Setter
@Embeddable
public class BookSentenceHasWord {

    @Column(name = "word")
    private String word;

    @Column(name = "after_word")
    private String afterWord;

    @Column(name = "part_of_speech")
    @Enumerated(EnumType.STRING)
    private PartOfSpeechEnum partOfSpeech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word translate;

    @Override
    public String toString() {
        return "BookSentenceHasWord{" +
            "afterWord='" + afterWord + '\'' +
            ", partOfSpeech='" + partOfSpeech + '\'' +
            ", translate=" + translate +
            '}';
    }
}
