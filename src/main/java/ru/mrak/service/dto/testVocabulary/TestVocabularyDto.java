package ru.mrak.service.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.TestVocabularyTypeEnum;

@Getter
@Setter
public abstract class TestVocabularyDto {
    private long testVocabularyId;

    public abstract TestVocabularyTypeEnum getType();
}
