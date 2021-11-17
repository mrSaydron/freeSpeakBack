package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemUserDictionaryService implements GalleryItem {

    private final Logger log = LoggerFactory.getLogger(GalleryItemUserDictionaryService.class);

    private static final GalleryItemDto USER_DICTIONARY_GALLERY = new GalleryItemDto()
        .setTitle("Словарь")
        .setType(GalleryItemTypeEnum.USER_DICTIONARY);

    @Override
    public Optional<GalleryItemDto> get() {
        log.debug("get user dictionary item");

        return Optional.of(USER_DICTIONARY_GALLERY);
    }
}
