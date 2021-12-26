package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.dto.gallery.item.GalleryItemTestVocabularyDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemTestVocabularyService implements GalleryItem<GalleryItemTestVocabularyDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemSentenceService.class);

    private static final GalleryItemTestVocabularyDto TEST_VOCABULARY;

    static {
        TEST_VOCABULARY = new GalleryItemTestVocabularyDto();
        TEST_VOCABULARY.setTitle("Тест словарного запаса");
    }

    @Override
    public Optional<GalleryItemTestVocabularyDto> get() {
        log.debug("get item test vocabulary");
        return Optional.of(TEST_VOCABULARY);
    }
}
