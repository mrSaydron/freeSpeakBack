package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.dto.gallery.GalleryDto;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.galleryItem.GalleryItemMarkSentenceService;
import ru.mrak.service.galleryItem.GalleryItemSentenceService;
import ru.mrak.service.galleryItem.GalleryItemWordCardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryDailyService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    private final GalleryItemWordCardService galleryItemWordCardService;
    private final GalleryItemSentenceService galleryItemSentenceService;
    private final GalleryItemMarkSentenceService galleryItemMarkSentenceService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get daily gallery");

        GalleryDto galleryDto = new GalleryDto()
            .setType(GalleryTypeEnum.DAILY)
            .setTitle(GalleryTypeEnum.DAILY.getTitle());

        galleryItemWordCardService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemSentenceService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));
        galleryItemMarkSentenceService.get().ifPresent(item -> galleryDto.getGalleryItems().add(item));

        return Optional.of(galleryDto);
    }

}
