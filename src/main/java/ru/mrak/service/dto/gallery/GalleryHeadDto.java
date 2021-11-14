package ru.mrak.service.dto.gallery;

import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import ru.mrak.model.enumeration.GalleryTypeEnum;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class GalleryHeadDto {
    private String title;
    private String url;
    private GalleryTypeEnum type;
    private Map<String, String> args = new HashMap<>();
}
