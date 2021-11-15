package ru.mrak.service.galleryPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.dto.gallery.GalleriesPageDto;
import ru.mrak.service.dto.gallery.GalleryHeadDto;

import java.util.Arrays;
import java.util.Optional;

import static ru.mrak.model.enumeration.GalleryTypeEnum.BOOK;
import static ru.mrak.model.enumeration.GalleryTypeEnum.DAILY;
import static ru.mrak.web.rest.GalleryController.GALLERY_CONTROLLER_URL;

@Service
public class GalleryPageService {

    private final Logger log = LoggerFactory.getLogger(GalleryPageService.class);

    /**
     * Возвращает заголовки галлерей для домашенй страницы пользователя
     */
    public Optional<GalleriesPageDto> get() {
        log.debug("get pages for user: todo");

        GalleryHeadDto dailyHead = new GalleryHeadDto()
            .setTitle("Задания на день")
            .setType(DAILY)
            .setUrl(GALLERY_CONTROLLER_URL + DAILY.getUrl());

        GalleryHeadDto booksHead = new GalleryHeadDto()
            .setTitle("Книги")
            .setType(GalleryTypeEnum.BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl());

        GalleriesPageDto galleriesPageDto = new GalleriesPageDto()
            .setGalleryHeads(Arrays.asList(dailyHead, booksHead));
        return Optional.of(galleriesPageDto);
    }
}
