package ru.mrak.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.mrak.service.gallery.GalleryFreeLearnService;
import ru.mrak.service.gallery.GalleryBookService;
import ru.mrak.service.gallery.GalleryDailyService;
import ru.mrak.dto.gallery.GalleryDto;

@RestController
@RequestMapping(GalleryController.GALLERY_CONTROLLER_URL)
@RequiredArgsConstructor
public class GalleryController {

    private final Logger log = LoggerFactory.getLogger(GalleryController.class);

    private final GalleryDailyService galleryDailyService;
    private final GalleryBookService galleryBookService;
    private final GalleryFreeLearnService galleryFreeLearnService;

    public final static String GALLERY_CONTROLLER_URL = "/api/gallery";
    public final static String DAILY_URL = "/daily";
    public final static String BOOK_URL = "/book";
    public final static String FREE_LEARN_URL = "/free-learn";

    @GetMapping(DAILY_URL)
    public GalleryDto getDailyGallery() {
        log.debug("GET daily gallery");
        return galleryDailyService.get().orElseThrow(RuntimeException::new);
    }

    @GetMapping(BOOK_URL)
    public GalleryDto getBooksGallery(
        @RequestParam(required = false, name = "filter-name") String filterName
    ) {
        log.debug("GET book gallery, filterName: {}", filterName);
        return galleryBookService.get(filterName).orElseThrow(RuntimeException::new);
    }

    @GetMapping(FREE_LEARN_URL)
    public GalleryDto getFreeLearnGallery() {
        log.debug("GET free learn gallery");
        return galleryFreeLearnService.get().orElseThrow(RuntimeException::new);
    }
}
