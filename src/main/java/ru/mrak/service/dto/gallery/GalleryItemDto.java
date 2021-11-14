package ru.mrak.service.dto.gallery;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GalleryItemDto {
    private String title;
    private String pictureUrl;
    private Map<String, String> args = new HashMap<>();
}
