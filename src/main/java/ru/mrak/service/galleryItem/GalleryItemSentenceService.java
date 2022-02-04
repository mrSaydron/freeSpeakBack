package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.dto.gallery.item.GalleryItemSentenceDto;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.service.UserSentencesService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemSentenceService implements GalleryItem<GalleryItemSentenceDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemSentenceService.class);

    private final UserSentencesService userSentencesService;

    private static final GalleryItemSentenceDto SENTENCE_GALLERY;

    static {
        SENTENCE_GALLERY = new GalleryItemSentenceDto();
        SENTENCE_GALLERY.setTitle("Предложения");
    }

    @Override
    public Optional<GalleryItemSentenceDto> get() {
        log.debug("get gallery item: sentence");
        Optional<GalleryItemSentenceDto> result = Optional.empty();

        List<BookSentence> sentenceForUser = userSentencesService.getMarkedSentences();
        if (sentenceForUser.size() > 0) {
            result = Optional.of(SENTENCE_GALLERY);
        }
        return result;
    }
}
