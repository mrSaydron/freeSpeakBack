package ru.mrak.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Объект для создания новой книги
 */
@Getter
@Setter
public class BookCreateDTO {

    @NotNull
    private String title;

    private String author;

    @NotNull
    private String text;

    private String pictureId;

    private Collection<TextTagDto> textTags = new ArrayList<>();
}
