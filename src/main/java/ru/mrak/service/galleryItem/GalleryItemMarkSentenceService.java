package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.book.BookSentenceService;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.dto.gallery.item.GalleryItemMarkSentenceDto;

import java.util.Optional;

/**
 * Сервис для формирования элемента галереи для изучения предложений из книги помеченной "для чтения"
 */
@Service
@RequiredArgsConstructor
public class GalleryItemMarkSentenceService implements GalleryItem<GalleryItemMarkSentenceDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemMarkSentenceService.class);

    private final BookSentenceService bookSentenceService;

    private static final GalleryItemMarkSentenceDto MARK_SENTENCE_GALLERY;

    static {
        MARK_SENTENCE_GALLERY = new GalleryItemMarkSentenceDto();
        MARK_SENTENCE_GALLERY.setTitle("Продолжить чтение");
    }

    @Override
    public Optional<GalleryItemMarkSentenceDto> get() {
        log.debug("get gallery item: mark sentence");
        Optional<GalleryItemMarkSentenceDto> result = Optional.empty();

        boolean hasMarkSentence = bookSentenceService.hasMarkSentence();
        if (hasMarkSentence) {
            result = Optional.of(MARK_SENTENCE_GALLERY);
        }
        return result;
    }
}
