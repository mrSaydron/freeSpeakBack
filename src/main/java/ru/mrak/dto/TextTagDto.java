package ru.mrak.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TextTagDto {
    private Long id;

    @Max(255)
    @Min(3)
    @NotEmpty
    private String tagName;

    @Max(255)
    @Min(3)
    @NotEmpty
    private String typeName;

    private TextTagDto parent;
}
