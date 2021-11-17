package ru.mrak.service.dto.gallery.item;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;

@Getter
@Setter
@Accessors(chain = true)
public class GalleryItemDto {
    private String title;
    private String pictureUrl;
    private GalleryItemTypeEnum type;
}
