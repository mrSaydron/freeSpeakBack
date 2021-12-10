package ru.mrak.service.dto.gallery.item;

import ru.mrak.model.enumeration.GalleryItemTypeEnum;

public class GalleryItemCardLearnDto extends GalleryItemDto {
    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.CARD_LEARN;
    }
}
