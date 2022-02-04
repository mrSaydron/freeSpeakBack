package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.dto.gallery.item.GalleryItemMarkSentenceDto;
import ru.mrak.service.UserMarkedBookService;

import java.util.Optional;

/**
 * Сервис для формирования элемента галереи для изучения предложений из книги помеченной "для чтения"
 */
@Service
@RequiredArgsConstructor
public class GalleryItemMarkBookSentenceService implements GalleryItem<GalleryItemMarkSentenceDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemMarkBookSentenceService.class);

    private final UserMarkedBookService userMarkedBookService;

    private static final GalleryItemMarkSentenceDto MARK_SENTENCE_GALLERY;

    static {
        MARK_SENTENCE_GALLERY = new GalleryItemMarkSentenceDto();
        MARK_SENTENCE_GALLERY.setTitle("Продолжить чтение");
    }

    @Override
    public Optional<GalleryItemMarkSentenceDto> get() {
        log.debug("get gallery item: mark sentence");
        Optional<GalleryItemMarkSentenceDto> result = Optional.empty();

        boolean hasMarkSentence = userMarkedBookService.hasMarkSentence();
        if (hasMarkSentence) {
            result = Optional.of(MARK_SENTENCE_GALLERY);
        }
        return result;
    }
}
