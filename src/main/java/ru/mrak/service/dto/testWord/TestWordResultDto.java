package ru.mrak.service.dto.testWord;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TestWordResultDto {
    private int vocabulary;
}
