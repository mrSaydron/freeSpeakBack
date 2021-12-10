package ru.mrak.service.dto.gallery.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;

@Getter
@Setter
@Accessors(chain = true)
public class GalleryItemTestVocabularyDto extends GalleryItemDto {
    @Override
    public GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.TEST_VOCABULARY;
    }
}
