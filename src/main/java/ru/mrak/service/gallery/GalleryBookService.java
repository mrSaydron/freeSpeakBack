package ru.mrak.service.gallery;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.mrak.galleryFilter.GalleryFilter;
import ru.mrak.model.enumeration.GalleryFilterEnum;
import ru.mrak.model.enumeration.GalleryTypeEnum;
import ru.mrak.service.GalleryFilterService;
import ru.mrak.service.book.BookQueryService;
import ru.mrak.dto.BookCriteria;
import ru.mrak.dto.gallery.GalleryDto;
import ru.mrak.dto.gallery.item.GalleryItemDto;
import ru.mrak.mapper.BookToGalleryItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryBookService implements Gallery {

    private final Logger log = LoggerFactory.getLogger(GalleryDailyService.class);

    private final BookToGalleryItemMapper bookToGalleryItemMapper;

    private final BookQueryService bookQueryService;
    private final GalleryFilterService galleryFilterService;

    @Override
    public Optional<GalleryDto> get() {
        return get(null);
    }

    @Override
    public Optional<GalleryDto> get(String filterName) {
        log.debug("get book gallery, filterName: {}", filterName);

        GalleryFilter galleryFilter = galleryFilterService.getFilter(GalleryTypeEnum.BOOK, filterName);
        BookCriteria bookCriteria = (BookCriteria) galleryFilter.getFilter(GalleryFilterEnum.BOOK_CRITERIA);
        Pageable pageable = (Pageable) galleryFilter.getFilter(GalleryFilterEnum.BOOK_PAGINATION);
        String title = (String) galleryFilter.getFilter(GalleryFilterEnum.TITLE);

        List<GalleryItemDto> galleryItems = bookQueryService.findByCriteria(bookCriteria, pageable).get()
            .map(bookToGalleryItemMapper::toDto)
            .collect(Collectors.toList());
        return Optional.of(
            new GalleryDto()
                .setTitle(title)
                .setType(GalleryTypeEnum.BOOK)
                .setGalleryItems(galleryItems)
        );
    }
}
