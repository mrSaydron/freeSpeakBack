package ru.mrak.dto.gallery;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.dto.gallery.item.GalleryItemDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class GalleryDto {
    private List<GalleryItemDto> galleryItems = new ArrayList<>();
    private GalleryTypeEnum type;
    private String title;
}
