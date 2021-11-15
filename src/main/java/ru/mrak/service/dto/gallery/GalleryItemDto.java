package ru.mrak.service.dto.gallery;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class GalleryItemDto {
    private String title;
    private String pictureUrl;
    private GalleryItemTypeEnum type;
    private Map<String, String> args = new HashMap<>();
}
