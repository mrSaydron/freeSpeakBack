package ru.mrak.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BookUserKnowId implements Serializable {
    private Long userId;
    private Long bookId;
    private Long bookDictionaryId;
}
