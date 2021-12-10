package ru.mrak.service.dto.gallery.item;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;

@Getter
@Setter
public abstract class GalleryItemDto {
    private String title;

    public abstract GalleryItemTypeEnum getType();
}
