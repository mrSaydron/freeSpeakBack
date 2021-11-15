package ru.mrak.service.gallery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.service.dto.gallery.GalleryDto;

import java.util.Optional;

@Service
public class GalleryBookService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get book gallery");
        return Optional.empty();
    }
}
