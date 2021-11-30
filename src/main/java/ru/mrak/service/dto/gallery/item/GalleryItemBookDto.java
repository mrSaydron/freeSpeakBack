package ru.mrak.service.dto.gallery.item;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GalleryItemBookDto extends GalleryItemDto {
    private String author;
    private Long id;
    private Double know;
    private Boolean isReading;
}
