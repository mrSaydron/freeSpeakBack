package ru.mrak.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
}
