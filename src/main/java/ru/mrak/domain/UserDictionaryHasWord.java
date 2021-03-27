package ru.mrak.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_dictionary_has_word")
public class UserDictionaryHasWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_dictionary_id")
    private UserDictionary dictionary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @Column(name = "priority")
    private Integer priority;

    @OneToMany(mappedBy = "dictionaryWord", fetch = FetchType.LAZY)
    private Collection<UserWordProgress> wordProgresses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(UserDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Collection<UserWordProgress> getWordProgresses() {
        return wordProgresses;
    }

    public void setWordProgresses(Collection<UserWordProgress> wordProgresses) {
        this.wordProgresses = wordProgresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDictionaryHasWord that = (UserDictionaryHasWord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserDictionaryHasWord{" +
            "id=" + id +
            ", dictionary=" + dictionary +
            ", word=" + word +
            ", priority=" + priority +
            ", wordProgresses=" + wordProgresses +
            '}';
    }
}
