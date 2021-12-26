package ru.mrak.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mrak.service.galleryPage.GalleryPageService;
import ru.mrak.dto.gallery.GalleriesPageDto;

@RestController
@RequestMapping("/api/galleries-page")
@RequiredArgsConstructor
public class GalleryPageController {

    private final Logger log = LoggerFactory.getLogger(GalleryPageController.class);

    private final GalleryPageService galleryPageService;

    @GetMapping
    public GalleriesPageDto get() {
        log.debug("GET gallery page");
        return galleryPageService.get().orElseThrow(RuntimeException::new);
    }
}
