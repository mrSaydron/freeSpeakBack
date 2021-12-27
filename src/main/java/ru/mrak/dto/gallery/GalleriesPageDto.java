package ru.mrak.dto.gallery;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Accessors(chain = true)
public class GalleriesPageDto {
    private Collection<GalleryHeadDto> galleryHeads = new ArrayList<>();
}
