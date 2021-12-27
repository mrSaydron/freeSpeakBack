package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.galleryFilter.GalleryFilter;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.dto.gallery.GalleryDto;
import ru.mrak.service.galleryItem.GalleryItemMarkBookService;
import ru.mrak.service.galleryItem.GalleryItemTestVocabularyService;
import ru.mrak.service.galleryItem.GalleryItemUserDictionaryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryFreeLearnService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryFreeLearnService.class);

    private final GalleryItemUserDictionaryService galleryItemUserDictionaryService;
    private final GalleryItemMarkBookService galleryItemMarkBookService;
    private final GalleryItemTestVocabularyService galleryItemTestVocabularyService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get free learn gallery");

        GalleryDto galleryDto = new GalleryDto()
            .setType(GalleryTypeEnum.FREE_LEARN)
            .setTitle(GalleryTypeEnum.FREE_LEARN.getTitle());

        galleryItemMarkBookService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemUserDictionaryService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemTestVocabularyService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));

        return Optional.of(galleryDto);
    }

    @Override
    public Optional<GalleryDto> get(String filterName) {
        return Optional.empty();
    }
}
