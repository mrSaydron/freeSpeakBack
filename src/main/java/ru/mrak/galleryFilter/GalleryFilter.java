package ru.mrak.galleryFilter;

import ru.mrak.model.enumeration.GalleryFilterEnum;

public interface GalleryFilter {
    Object getFilter(GalleryFilterEnum filter);
}
