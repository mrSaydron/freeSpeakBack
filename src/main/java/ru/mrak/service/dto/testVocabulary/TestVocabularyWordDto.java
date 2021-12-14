package ru.mrak.service.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;
import ru.mrak.service.dto.WordDto;

@Getter
@Setter
@ToString
public class TestVocabularyWordDto extends TestVocabularyDto {
    private WordDto word;

    @Override
    public TestVocabularyTypeEnum getType() {
        return TestVocabularyTypeEnum.WORD;
    }

}
