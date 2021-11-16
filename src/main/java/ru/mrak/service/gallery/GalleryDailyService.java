package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.dto.gallery.GalleryDto;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.galleryItem.GalleryItemSentenceService;
import ru.mrak.service.galleryItem.GalleryItemWordCardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryDailyService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    private final GalleryItemWordCardService galleryItemWordCardService;
    private final GalleryItemSentenceService galleryItemSentenceService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get daily gallery");

        GalleryDto galleryDto = new GalleryDto()
            .setType(GalleryTypeEnum.DAILY)
            .setTitle(GalleryTypeEnum.DAILY.getTitle());

        Optional<GalleryItemDto> galleryItemWordCard = galleryItemWordCardService.get();
        galleryItemWordCard.ifPresent(item -> galleryDto.getGalleryItems().add(item));

        Optional<GalleryItemDto> galleryItemSentence = galleryItemSentenceService.get();
        galleryItemSentence.ifPresent(item -> galleryDto.getGalleryItems().add(item));

        return Optional.of(galleryDto);
    }

}
