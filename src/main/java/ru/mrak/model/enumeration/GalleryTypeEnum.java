package ru.mrak.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.mrak.web.rest.GalleryController.BOOKS_URL;
import static ru.mrak.web.rest.GalleryController.DAILY_URL;

@Getter
@AllArgsConstructor
public enum GalleryTypeEnum {
    DAILY(DAILY_URL),
    BOOKS(BOOKS_URL),
    ;

    private final String url;
}
