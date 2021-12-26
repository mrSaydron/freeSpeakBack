package ru.mrak.dto.gallery.item;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;

@Getter
@Setter
@Accessors(chain = true)
public class GalleryItemBookDto extends GalleryItemDto {
    private String pictureUrl;
    private String author;
    private Long id;
    private Double know;
    private Boolean isReading;

    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.BOOK;
    }
}
