package ru.mrak.service.gallery;

import ru.mrak.dto.gallery.GalleryDto;

import java.util.Optional;

public interface Gallery {
    Optional<GalleryDto> get();
}
