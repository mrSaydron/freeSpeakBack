package ru.mrak.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.dto.gallery.GalleriesPageDto;
import ru.mrak.service.dto.gallery.GalleryHeadDto;

import java.util.Arrays;

import static ru.mrak.model.enumeration.GalleryTypeEnum.BOOKS;
import static ru.mrak.model.enumeration.GalleryTypeEnum.DAILY;
import static ru.mrak.web.rest.GalleryController.GALLERY_CONTROLLER_URL;

@Service
public class GalleryPageService {

    private final Logger log = LoggerFactory.getLogger(GalleryPageService.class);

    /**
     * Возвращает заголовки галлерей для домашенй страницы пользователя
     */
    public GalleriesPageDto get() {
        log.debug("get pages for user: todo");

        GalleryHeadDto dailyHead = GalleryHeadDto.builder()
            .title("Задания на день")
            .type(DAILY)
            .url(GALLERY_CONTROLLER_URL + DAILY.getUrl())
            .build();

        GalleryHeadDto booksHead = GalleryHeadDto.builder()
            .title("Книги")
            .type(GalleryTypeEnum.BOOKS)
            .url(GALLERY_CONTROLLER_URL + BOOKS.getUrl())
            .build();

        return GalleriesPageDto.builder()
            .galleryHeads(Arrays.asList(dailyHead, booksHead))
            .build();
    }
}
