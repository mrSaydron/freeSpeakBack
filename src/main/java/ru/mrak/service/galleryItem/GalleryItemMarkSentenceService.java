package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.book.BookSentenceService;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;

import java.util.Optional;

/**
 * Сервис для формирования элемента галереи для изучения предложений из книги помеченной "для чтения"
 */
@Service
@RequiredArgsConstructor
public class GalleryItemMarkSentenceService implements GalleryItem<GalleryItemDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemMarkSentenceService.class);

    private final BookSentenceService bookSentenceService;

    private static final GalleryItemDto MARK_SENTENCE_GALLERY = new GalleryItemDto()
        .setTitle("Продолжить чтение")
        .setType(GalleryItemTypeEnum.MARK_SENTENCE);

    @Override
    public Optional<GalleryItemDto> get() {
        log.debug("get gallery item: mark sentence");
        Optional<GalleryItemDto> result = Optional.empty();

        boolean hasMarkSentence = bookSentenceService.hasMarkSentence();
        if (hasMarkSentence) {
            result = Optional.of(MARK_SENTENCE_GALLERY);
        }
        return result;
    }
}
