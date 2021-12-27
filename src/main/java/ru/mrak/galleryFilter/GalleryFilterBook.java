package ru.mrak.galleryFilter;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import ru.mrak.dto.BookCriteria;
import ru.mrak.model.enumeration.GalleryFilterEnum;

@Data
public class GalleryFilterBook implements GalleryFilter {

    private BookCriteria bookCriteria;
    private Pageable pageable;
    private String title;

    @Override
    public Object getFilter(GalleryFilterEnum filter) {
        switch (filter) {
            case TITLE:
                return title;
            case BOOK_CRITERIA:
                return bookCriteria;
            case BOOK_PAGINATION:
                return pageable;
            default:
                throw new RuntimeException();
        }
    }
}
