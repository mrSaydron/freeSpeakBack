package ru.mrak.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BookSentenceHasWordDTO {
    private String word;
    private String afterWord;
    private Long translateId;
    private PartOfSpeechEnum partOfSpeech;
}
