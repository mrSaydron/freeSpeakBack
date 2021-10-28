package ru.mrak.model.entity;

import ru.mrak.model.enumeration.PartOfSpeechEnum;

import javax.persistence.*;

// book_sentence_has_word

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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAfterWord() {
        return afterWord;
    }

    public void setAfterWord(String afterWord) {
        this.afterWord = afterWord;
    }

    public PartOfSpeechEnum getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeechEnum partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Word getTranslate() {
        return translate;
    }

    public void setTranslate(Word translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "BookSentenceHasWord{" +
            "afterWord='" + afterWord + '\'' +
            ", partOfSpeech='" + partOfSpeech + '\'' +
            ", translate=" + translate +
            '}';
    }
}
