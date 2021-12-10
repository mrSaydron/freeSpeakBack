package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.dto.gallery.item.GalleryItemUserDictionaryDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemUserDictionaryService implements GalleryItem<GalleryItemUserDictionaryDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemUserDictionaryService.class);

    private static final GalleryItemUserDictionaryDto USER_DICTIONARY_GALLERY;

    static {
        USER_DICTIONARY_GALLERY = new GalleryItemUserDictionaryDto();
        USER_DICTIONARY_GALLERY.setTitle("Словарь");
    }

    @Override
    public Optional<GalleryItemUserDictionaryDto> get() {
        log.debug("get user dictionary item");

        return Optional.of(USER_DICTIONARY_GALLERY);
    }
}
