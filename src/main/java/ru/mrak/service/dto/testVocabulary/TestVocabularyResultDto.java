package ru.mrak.service.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;

@Getter
@Setter
public class TestVocabularyResultDto extends TestVocabularyDto {
    private TestWordResultDto result;

    @Override
    public TestVocabularyTypeEnum getType() {
        return TestVocabularyTypeEnum.RESULT;
    }
}
