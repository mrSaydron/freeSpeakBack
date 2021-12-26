package ru.mrak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextTagDto {
    private Long id;
    private String tagName;
    private String typeName;
    private TextTagDto parent;
}
