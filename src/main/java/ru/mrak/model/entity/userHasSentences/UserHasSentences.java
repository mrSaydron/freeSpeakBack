package ru.mrak.model.entity.userHasSentences;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_has_sentences")
@IdClass(UserHasSentencesId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserHasSentences {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "book_sentence_id")
    private Long bookSentenceId;

    @Column(name = "successful_last_date")
    private LocalDateTime successfulLastDate;

    @Column(name = "mark_date")
    private LocalDateTime markDate;
}
