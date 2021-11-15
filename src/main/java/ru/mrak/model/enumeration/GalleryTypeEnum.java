package ru.mrak.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.mrak.web.rest.GalleryController.BOOK_URL;
import static ru.mrak.web.rest.GalleryController.DAILY_URL;

@Getter
@AllArgsConstructor
public enum GalleryTypeEnum {
    DAILY(DAILY_URL, "Задания на день"),
    BOOK(BOOK_URL, "Книги"),
    ;

    private final String url;
    private final String title;
}
