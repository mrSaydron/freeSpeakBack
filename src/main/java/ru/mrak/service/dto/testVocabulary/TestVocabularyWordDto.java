package ru.mrak.service.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;
import ru.mrak.service.dto.WordDto;

@Getter
@Setter
public class TestVocabularyWordDto extends TestVocabularyDto {
    private WordDto word;

    @Override
    public TestVocabularyTypeEnum getTestWordType() {
        return TestVocabularyTypeEnum.WORD;
    }
}
