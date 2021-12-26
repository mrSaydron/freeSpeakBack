package ru.mrak.service.galleryPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.dto.gallery.GalleriesPageDto;
import ru.mrak.dto.gallery.GalleryHeadDto;

import java.util.Arrays;
import java.util.Optional;

import static ru.mrak.model.enumeration.GalleryTypeEnum.*;
import static ru.mrak.controller.GalleryController.GALLERY_CONTROLLER_URL;

@Service
public class GalleryPageService {

    private final Logger log = LoggerFactory.getLogger(GalleryPageService.class);

    /**
     * Возвращает заголовки галлерей для домашенй страницы пользователя
     */
    public Optional<GalleriesPageDto> get() {
        log.debug("get pages for user: todo");

        GalleryHeadDto dailyHead = new GalleryHeadDto()
            .setTitle(DAILY.getTitle())
            .setType(DAILY)
            .setUrl(GALLERY_CONTROLLER_URL + DAILY.getUrl());

        GalleryHeadDto freeLearnHead = new GalleryHeadDto()
            .setTitle(FREE_LEARN.getTitle())
            .setType(FREE_LEARN)
            .setUrl(GALLERY_CONTROLLER_URL + FREE_LEARN.getUrl());

        GalleryHeadDto booksHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl());

        GalleriesPageDto galleriesPageDto = new GalleriesPageDto()
            .setGalleryHeads(Arrays.asList(dailyHead, freeLearnHead, booksHead));
        return Optional.of(galleriesPageDto);
    }
}
