package ru.mrak.web.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mrak.service.GalleryService;
import ru.mrak.service.dto.gallery.GalleryDto;

@RestController
@RequestMapping(GalleryController.GALLERY_CONTROLLER_URL)
@RequiredArgsConstructor
public class GalleryController {

    private final Logger log = LoggerFactory.getLogger(GalleryController.class);

    private final GalleryService galleryService;

    public final static String GALLERY_CONTROLLER_URL = "/api/gallery";
    public final static String DAILY_URL = "/daily";
    public final static String BOOKS_URL = "/books";

    @GetMapping(DAILY_URL)
    public GalleryDto getDailyGallery() {
        return new GalleryDto();
    }

    @GetMapping(BOOKS_URL)
    public GalleryDto getBooksGallery() {
        return new GalleryDto();
    }
}
