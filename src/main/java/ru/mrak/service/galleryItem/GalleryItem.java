package ru.mrak.service.galleryItem;

import ru.mrak.service.dto.gallery.item.GalleryItemDto;

import java.util.Optional;

public interface GalleryItem<T extends GalleryItemDto> {
    Optional<T> get();
}
