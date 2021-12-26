package ru.mrak.dto.gallery.item;

import ru.mrak.model.enumeration.GalleryItemTypeEnum;

public class GalleryItemUserDictionaryDto extends GalleryItemDto {
    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.USER_DICTIONARY;
    }
}
