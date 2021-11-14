package ru.mrak.service.dto.gallery;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GalleriesPageDto {
    private List<GalleryHeadDto> galleryHeads;
}
