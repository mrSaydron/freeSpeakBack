package ru.mrak.service.dto.gallery;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
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
