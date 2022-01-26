package ru.mrak.service;

import io.github.jhipster.service.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.dto.BookCriteria;
import ru.mrak.galleryFilter.GalleryFilter;
import ru.mrak.galleryFilter.GalleryFilterBook;
import ru.mrak.model.enumeration.GalleryTypeEnum;

import java.util.Arrays;

/**
 * Возвращает фильтры для галерей по типу галереи и имени фильтра
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GalleryFilterService {

    public GalleryFilter getFilter(GalleryTypeEnum galleryType, String filterName) {
        GalleryFilter result = null;
        switch (galleryType) {
            case BOOK:
                if (filterName == null) {
                    result = getBookDefault();
                } else {
                    switch (filterName) {
                        case "fantasy": result = getBookFantasy(); break;
                        case "children-book":  result = getBookChildrenBook(); break;
                        case "detective":  result = getBookDetectiveBook(); break;
                        case "adventure":  result = getBookAdventureBook(); break;
                        case "another-tag":  result = getBookAnotherTagBook(); break;
                        default: throw new RuntimeException();
                    }
                }
                break;
            case DAILY:
            case FREE_LEARN:
                throw new RuntimeException();
        }

        return result;
    }


    private GalleryFilter getBookDefault() {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        galleryFilterBook.setBookCriteria(new BookCriteria());
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Книги");
        return galleryFilterBook;
    }

    private GalleryFilter getBookFantasy () {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        BookCriteria bookCriteria = new BookCriteria();
        Filter<Long> textTagFilter = new Filter<>();
        textTagFilter.setIn(Arrays.asList(5L));
        bookCriteria.setTextTag(textTagFilter);
        galleryFilterBook.setBookCriteria(bookCriteria);
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Фантастика");
        return galleryFilterBook;
    }

    private GalleryFilter getBookChildrenBook () {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        BookCriteria bookCriteria = new BookCriteria();
        Filter<Long> textTagFilter = new Filter<>();
        textTagFilter.setIn(Arrays.asList(25L));
        bookCriteria.setTextTag(textTagFilter);
        galleryFilterBook.setBookCriteria(bookCriteria);
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Детские книги");
        return galleryFilterBook;
    }

    private GalleryFilter getBookDetectiveBook() {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        BookCriteria bookCriteria = new BookCriteria();
        Filter<Long> textTagFilter = new Filter<>();
        textTagFilter.setIn(Arrays.asList(1L));
        bookCriteria.setTextTag(textTagFilter);
        galleryFilterBook.setBookCriteria(bookCriteria);
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Детективы");
        return galleryFilterBook;
    }

    private GalleryFilter getBookAdventureBook() {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        BookCriteria bookCriteria = new BookCriteria();
        Filter<Long> textTagFilter = new Filter<>();
        textTagFilter.setIn(Arrays.asList(8L));
        bookCriteria.setTextTag(textTagFilter);
        galleryFilterBook.setBookCriteria(bookCriteria);
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Приключения");
        return galleryFilterBook;
    }

    private GalleryFilter getBookAnotherTagBook() {
        GalleryFilterBook galleryFilterBook = new GalleryFilterBook();
        BookCriteria bookCriteria = new BookCriteria();
        Filter<Long> textTagFilter = new Filter<>();
        textTagFilter.setNotIn(Arrays.asList(1L, 5L, 8L, 25L));
        bookCriteria.setTextTag(textTagFilter);
        galleryFilterBook.setBookCriteria(bookCriteria);
        galleryFilterBook.setPageable(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))).first());
        galleryFilterBook.setTitle("Другие");
        return galleryFilterBook;
    }
}
