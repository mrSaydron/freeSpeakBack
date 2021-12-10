package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.entity.UserSettings;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.UserSettingsService;
import ru.mrak.service.dto.gallery.GalleryDto;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.galleryItem.GalleryItemMarkSentenceService;
import ru.mrak.service.galleryItem.GalleryItemSentenceService;
import ru.mrak.service.galleryItem.GalleryItemTestVocabularyService;
import ru.mrak.service.galleryItem.GalleryItemWordCardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryDailyService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    private final UserSettingsService userSettingsService;

    private final GalleryItemWordCardService galleryItemWordCardService;
    private final GalleryItemSentenceService galleryItemSentenceService;
    private final GalleryItemMarkSentenceService galleryItemMarkSentenceService;
    private final GalleryItemTestVocabularyService galleryItemTestVocabularyService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get daily gallery");

        GalleryDto galleryDto = new GalleryDto()
            .setType(GalleryTypeEnum.DAILY)
            .setTitle(GalleryTypeEnum.DAILY.getTitle());

        UserSettings userSettings = userSettingsService.get();
        if (!userSettings.isTestedVocabulary()) {
            galleryItemTestVocabularyService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        }

        galleryItemWordCardService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemSentenceService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemMarkSentenceService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));

        return Optional.of(galleryDto);
    }

}
