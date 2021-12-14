package ru.mrak.service.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;

@Getter
@Setter
@ToString
public class TestVocabularyResultDto extends TestVocabularyDto {
    private TestWordResultDto result;

    @Override
    public TestVocabularyTypeEnum getType() {
        return TestVocabularyTypeEnum.RESULT;
    }

    @Override
    public String toString() {
        return "TestVocabularyResultDto{" +
            "result=" + result +
            '}';
    }
}
