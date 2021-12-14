package ru.mrak.service.dto.userWord;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.service.dto.WordDto;

import java.util.List;

@Getter
@Setter
public class UserWordDto {
    private Long id;
    private WordDto word;
    private List<WordProgressDTO> wordProgresses;
    private boolean fromTest;
}
