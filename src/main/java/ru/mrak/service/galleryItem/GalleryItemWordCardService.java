package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.service.UserWordService;
import ru.mrak.dto.gallery.item.GalleryItemCardLearnDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemWordCardService implements GalleryItem<GalleryItemCardLearnDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemWordCardService.class);

    private final UserWordService userWordService;

    private static final GalleryItemCardLearnDto WORD_CARD_GALLERY;

    static {
        WORD_CARD_GALLERY = new GalleryItemCardLearnDto();
        WORD_CARD_GALLERY.setTitle("Изучение слов");
    }

    @Override
    public Optional<GalleryItemCardLearnDto> get() {
        log.debug("get gallery item: word card");
        Optional<GalleryItemCardLearnDto> result = Optional.empty();

        List<UserWord> wordsOfDay = userWordService.getWordsOfDay();
        int leftHearts = userWordService.getLeftHearts();

        if (leftHearts > 0 || wordsOfDay.size() > 0) {
            result = Optional.of(WORD_CARD_GALLERY);
        }
        return result;
    }
}
