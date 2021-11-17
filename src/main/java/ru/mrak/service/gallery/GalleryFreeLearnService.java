package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.dto.gallery.GalleryDto;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.galleryItem.GalleryItemUserDictionaryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryFreeLearnService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryFreeLearnService.class);

    private final GalleryItemUserDictionaryService galleryItemUserDictionaryService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get free learn gallery");

        GalleryDto galleryDto = new GalleryDto()
            .setType(GalleryTypeEnum.FREE_LEARN)
            .setTitle(GalleryTypeEnum.FREE_LEARN.getTitle());

        Optional<GalleryItemDto> galleryUserDictionary = galleryItemUserDictionaryService.get();
        galleryUserDictionary.ifPresent(item -> galleryDto.getGalleryItems().add(item));

        return Optional.of(galleryDto);
    }
}
