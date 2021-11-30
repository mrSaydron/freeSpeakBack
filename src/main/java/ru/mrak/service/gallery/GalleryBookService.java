package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.book.BookQueryService;
import ru.mrak.service.dto.BookCriteria;
import ru.mrak.service.dto.gallery.GalleryDto;
import ru.mrak.service.dto.gallery.item.GalleryItemDto;
import ru.mrak.service.mapper.BookToGalleryItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryBookService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    private final BookToGalleryItemMapper bookToGalleryItemMapper;

    private final BookQueryService bookQueryService;

    @Override
    public Optional<GalleryDto> get() {
        log.debug("get book gallery");

        BookCriteria criteria = new BookCriteria();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first();
        List<GalleryItemDto> galleryItems = bookQueryService.findByCriteria(criteria, pageable).get()
            .map(bookToGalleryItemMapper::toDto)
            .collect(Collectors.toList());
        return Optional.of(
            new GalleryDto()
            .setTitle(GalleryTypeEnum.BOOK.getTitle())
            .setType(GalleryTypeEnum.BOOK)
            .setGalleryItems(galleryItems)
        );
    }
}
