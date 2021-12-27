package ru.mrak.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.mrak.controller.BookController;
import ru.mrak.model.entity.Book;
import ru.mrak.controller.filter.StringRangeFilter;
import ru.mrak.model.entity.TextTag;

/**
 * Criteria class for the {@link Book} entity. This class is used
 * in {@link BookController} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@Data
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringRangeFilter title;
    private StringRangeFilter author;
    private Filter<Long> textTag;

    private StringFilter titleAuthor; // Строка для поиска по названию и автору
    private DoubleFilter know;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.textTag = other.textTag == null ? null : other.textTag.copy();

        this.titleAuthor = other.titleAuthor == null ? null : other.titleAuthor.copy();
        this.know = other.know == null ? null : other.know.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }
}
