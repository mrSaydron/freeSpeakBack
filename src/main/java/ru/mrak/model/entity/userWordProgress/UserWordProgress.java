package ru.mrak.model.entity.userWordProgress;

import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "user_word_progress")
@IdClass(UserWordProgressId.class)
public class UserWordProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "word_id", insertable = false, updatable = false)
    private Long wordId;

    @Id
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserWordProgressTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "word_id", insertable = false, updatable = false)
    private Word word;

    @Column(name = "successful_attempts")
    private int successfulAttempts;

    @Column(name = "box_number")
    private int boxNumber;

    @Column(name = "fail_last_date")
    private Instant failLastDate;

    @Column(name = "successful_last_date")
    private Instant successfulLastDate;

    public UserWordProgress() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
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

    public Instant getFailLastDate() {
        return failLastDate;
    }

    public void setFailLastDate(Instant failLastDate) {
        this.failLastDate = failLastDate;
    }

    public Instant getSuccessfulLastDate() {
        return successfulLastDate;
    }

    public void setSuccessfulLastDate(Instant successLastDate) {
        this.successfulLastDate = successLastDate;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWordProgress that = (UserWordProgress) o;
        return userId.equals(that.userId) && wordId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserWordProgress{" +
            ", type=" + type +
            ", successfulAttempts=" + successfulAttempts +
            ", boxNumber=" + boxNumber +
            ", failLastDate=" + failLastDate +
            ", successLastDate=" + successfulLastDate +
            '}';
    }
}
