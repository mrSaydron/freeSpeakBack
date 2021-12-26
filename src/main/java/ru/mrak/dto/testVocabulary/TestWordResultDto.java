package ru.mrak.dto.testVocabulary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TestWordResultDto {
    private int vocabulary;

    @Override
    public String toString() {
        return "TestWordResultDto{" +
            "vocabulary=" + vocabulary +
            '}';
    }
}
