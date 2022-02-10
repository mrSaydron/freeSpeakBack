package ru.mrak.dto.userWord;

import lombok.Data;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;

import java.time.LocalDateTime;

@Data
public class WordProgressDTO {
    private Long id;
    private UserWordProgressTypeEnum type;
    private int successfulAttempts;
    private int boxNumber;
    private LocalDateTime failLastDate;
    private LocalDateTime successLastDate;
}
