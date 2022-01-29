package ru.mrak.model.entity.userWordProgress;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
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

    @Column(name = "fail_attempts")
    private int failAttempts;

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
