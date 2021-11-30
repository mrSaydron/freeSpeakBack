package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.book.BookSentenceService;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemSentenceService implements GalleryItem<GalleryItemDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemSentenceService.class);

    private final BookSentenceService bookSentenceService;

    private static final GalleryItemDto SENTENCE_GALLERY = new GalleryItemDto()
        .setTitle("Предложения")
        .setType(GalleryItemTypeEnum.SENTENCE);

    @Override
    public Optional<GalleryItemDto> get() {
        log.debug("get gallery item: sentence");
        Optional<GalleryItemDto> result = Optional.empty();

        List<BookSentence> sentenceForUser = bookSentenceService.getSentenceForUser();
        if (sentenceForUser.size() > 0) {
            result = Optional.of(SENTENCE_GALLERY);
        }
        return result;
    }
}
