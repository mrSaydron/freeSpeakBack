package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.UserWordService;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryItemWordCardService implements GalleryItem {

    private final Logger log = LoggerFactory.getLogger(GalleryItemWordCardService.class);

    private final UserWordService userWordService;

    private static final GalleryItemDto WORD_CARD_GALLERY = new GalleryItemDto()
        .setTitle("Изучение слов")
        .setType(GalleryItemTypeEnum.CARD_LEARN);

    @Override
    public Optional<GalleryItemDto> get() {
        log.debug("get gallery item: word card");
        Optional<GalleryItemDto> result = Optional.empty();

        List<UserWord> wordsOfDay = userWordService.getWordsOfDay();
        int leftHearts = userWordService.getLeftHearts();

        if (leftHearts > 0 || wordsOfDay.size() > 0) {
            result = Optional.of(WORD_CARD_GALLERY);
        }
        return result;
    }
}
