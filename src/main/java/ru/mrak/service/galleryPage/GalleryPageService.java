package ru.mrak.service.galleryPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.dto.gallery.GalleriesPageDto;
import ru.mrak.dto.gallery.GalleryHeadDto;

import java.util.Optional;

import static ru.mrak.controller.GalleryController.GALLERY_CONTROLLER_URL;
import static ru.mrak.model.enumeration.GalleryTypeEnum.*;

@Service
public class GalleryPageService {

    private final Logger log = LoggerFactory.getLogger(GalleryPageService.class);

    /**
     * Возвращает заголовки галлерей для домашенй страницы пользователя
     */
    public Optional<GalleriesPageDto> get() {
        log.debug("get pages for user: todo");
        GalleriesPageDto galleriesPageDto = new GalleriesPageDto();

        GalleryHeadDto dailyHead = new GalleryHeadDto()
            .setTitle(DAILY.getTitle())
            .setType(DAILY)
            .setUrl(GALLERY_CONTROLLER_URL + DAILY.getUrl());
        galleriesPageDto.getGalleryHeads().add(dailyHead);

        GalleryHeadDto freeLearnHead = new GalleryHeadDto()
            .setTitle(FREE_LEARN.getTitle())
            .setType(FREE_LEARN)
            .setUrl(GALLERY_CONTROLLER_URL + FREE_LEARN.getUrl());
        galleriesPageDto.getGalleryHeads().add(freeLearnHead);

        GalleryHeadDto booksFantasyHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl() + "?filter-name=fantasy");
        galleriesPageDto.getGalleryHeads().add(booksFantasyHead);

        GalleryHeadDto booksDetectiveHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl() + "?filter-name=detective");
        galleriesPageDto.getGalleryHeads().add(booksDetectiveHead);

        GalleryHeadDto booksAdventureHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl() + "?filter-name=adventure");
        galleriesPageDto.getGalleryHeads().add(booksAdventureHead);

        GalleryHeadDto booksChildrenBookHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl() + "?filter-name=children-book");
        galleriesPageDto.getGalleryHeads().add(booksChildrenBookHead);

        GalleryHeadDto booksAnotherTagBookHead = new GalleryHeadDto()
            .setTitle(BOOK.getTitle())
            .setType(BOOK)
            .setUrl(GALLERY_CONTROLLER_URL + BOOK.getUrl() + "?filter-name=another-tag");
        galleriesPageDto.getGalleryHeads().add(booksAnotherTagBookHead);

        return Optional.of(galleriesPageDto);
    }
}
