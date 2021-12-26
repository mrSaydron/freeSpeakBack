package ru.mrak.dto.gallery.item;

import ru.mrak.model.enumeration.GalleryItemTypeEnum;

public class GalleryItemSentenceDto extends GalleryItemDto {
    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.SENTENCE;
    }
}
