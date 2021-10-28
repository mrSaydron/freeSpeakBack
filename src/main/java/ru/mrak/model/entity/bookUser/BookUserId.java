package ru.mrak.model.entity.bookUser;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BookUserId implements Serializable {
    private Long userId;
    private Long bookId;
}
