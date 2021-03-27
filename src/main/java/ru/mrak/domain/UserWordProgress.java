package ru.mrak.domain;

import ru.mrak.domain.enumeration.UserWordProgressTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_word_progress")
public class UserWordProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_dictionary_has_word_id")
    private UserDictionaryHasWord dictionaryWord;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserWordProgressTypeEnum type;

    @Column(name = "successful_attempts")
    private int successfulAttempts;

    @Column(name = "box_number")
    private int boxNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDictionaryHasWord getDictionaryWord() {
        return dictionaryWord;
    }

    public void setDictionaryWord(UserDictionaryHasWord dictionaryWord) {
        this.dictionaryWord = dictionaryWord;
    }

    public UserWordProgressTypeEnum getType() {
        return type;
    }

    public void setType(UserWordProgressTypeEnum type) {
        this.type = type;
    }

    public int getSuccessfulAttempts() {
        return successfulAttempts;
    }

    public void setSuccessfulAttempts(int successfulAttempts) {
        this.successfulAttempts = successfulAttempts;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWordProgress that = (UserWordProgress) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserWordProgress{" +
            "id=" + id +
            ", dictionaryWord=" + dictionaryWord +
            ", type=" + type +
            ", successfulAttempts=" + successfulAttempts +
            ", boxNumber=" + boxNumber +
            '}';
    }
}
