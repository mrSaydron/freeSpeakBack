package ru.mrak.service.dto.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;
import ru.mrak.service.dto.WordDto;

@Getter
@Setter
@Accessors(chain = true)
public class TestVocabularyDto {
    private TestVocabularyTypeEnum testWordType;
    private long testVocabularyId;
    private WordDto word;
    private TestWordResultDto testWordResult;
}
