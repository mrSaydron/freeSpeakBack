package ru.mrak.service.dto.gallery;

import lombok.Data;
import ru.mrak.model.enumeration.GalleryTypeEnum;

import java.util.ArrayList;
import java.util.List;

@Data
public class GalleryDto {
    private List<GalleryItemDto> galleryItems = new ArrayList<>();
    private GalleryTypeEnum type;
}
