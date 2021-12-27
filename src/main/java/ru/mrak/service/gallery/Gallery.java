package ru.mrak.service.gallery;

import ru.mrak.dto.gallery.GalleryDto;
import ru.mrak.galleryFilter.GalleryFilter;

import java.util.Optional;

public interface Gallery {
    Optional<GalleryDto> get();
    Optional<GalleryDto> get(String filterName);
}
