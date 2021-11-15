package ru.mrak.service.dto.gallery;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GalleriesPageDto {
    private List<GalleryHeadDto> galleryHeads;
}
