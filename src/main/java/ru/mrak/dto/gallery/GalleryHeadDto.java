package ru.mrak.dto.gallery;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryTypeEnum;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class GalleryHeadDto {
    private String title;
    private String url;
    private GalleryTypeEnum type;
    private Map<String, String> args = new HashMap<>();
}
