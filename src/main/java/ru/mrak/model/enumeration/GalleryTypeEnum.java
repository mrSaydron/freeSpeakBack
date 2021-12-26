package ru.mrak.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.mrak.controller.GalleryController.*;

@Getter
@AllArgsConstructor
public enum GalleryTypeEnum {
    DAILY(DAILY_URL, "Задания на день"),
    BOOK(BOOK_URL, "Книги"),
    FREE_LEARN(FREE_LEARN_URL, "Свободное изучение")
    ;

    private final String url;
    private final String title;
}
