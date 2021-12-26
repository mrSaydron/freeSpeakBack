package ru.mrak.dto.gallery.item;

import ru.mrak.model.enumeration.GalleryItemTypeEnum;

public class GalleryItemMarkSentenceDto extends GalleryItemDto {
    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.MARK_SENTENCE;
    }
}
