package ru.mrak.model.entity.userHasSentences;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_has_sentences")
@IdClass(UserHasSentencesId.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserHasSentences {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "book_sentence_id")
    private Long bookSentenceId;

    @Column(name = "successful_last_date")
    private Instant successfulLastDate;

    @Column(name = "fail_last_date")
    private Instant failLastDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookSentenceId() {
        return bookSentenceId;
    }

    public void setBookSentenceId(Long bookSentenceId) {
        this.bookSentenceId = bookSentenceId;
    }

    public Instant getSuccessfulLastDate() {
        return successfulLastDate;
    }

    public void setSuccessfulLastDate(Instant successfulLastDate) {
        this.successfulLastDate = successfulLastDate;
    }

    public Instant getFailLastDate() {
        return failLastDate;
    }

    public void setFailLastDate(Instant failLastDate) {
        this.failLastDate = failLastDate;
    }
}
