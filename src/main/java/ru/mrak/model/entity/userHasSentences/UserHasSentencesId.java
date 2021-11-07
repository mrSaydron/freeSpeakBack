package ru.mrak.model.entity.userHasSentences;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserHasSentencesId implements Serializable {
    private Long userId;
    private Long bookSentenceId;
}
