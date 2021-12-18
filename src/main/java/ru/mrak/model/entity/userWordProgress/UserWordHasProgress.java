package ru.mrak.model.entity.userWordProgress;

import ru.mrak.model.enumeration.UserWordProgressTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Embeddable
@Table(name = "user_word_has_progress")
public class UserWordHasProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserWordProgressTypeEnum type;

    @Column(name = "successful_attempts")
    private int successfulAttempts;

    @Column(name = "box_number")
    private int boxNumber;

    @Column(name = "fail_last_date")
    private Instant failLastDate;

    @Column(name = "successful_last_date")
    private Instant successfulLastDate;

    public UserWordHasProgress() {
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
