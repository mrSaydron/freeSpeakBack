package ru.mrak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;
import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.entity.Book;
import ru.mrak.web.rest.filter.StringRangeFilter;

/**
 * Criteria class for the {@link Book} entity. This class is used
 * in {@link ru.mrak.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@Getter
@Setter
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringRangeFilter title;
    private StringRangeFilter author;

    private StringFilter titleAuthorFilter; // Строка для поиска по названию и автору
    private DoubleFilter knowFilter;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.author = other.author == null ? null : other.author.copy();

        this.titleAuthorFilter = other.titleAuthorFilter == null ? null : other.titleAuthorFilter.copy();
        this.knowFilter = other.knowFilter == null ? null : other.knowFilter.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(titleAuthorFilter, that.titleAuthorFilter) &&
                Objects.equals(knowFilter, that.knowFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            author,
            titleAuthorFilter,
            knowFilter
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (titleAuthorFilter != null ? "titleAuthor=" + titleAuthorFilter + ", " : "") +
                (knowFilter != null ? "orPublicBook=" + knowFilter + ", " : "") +
            "}";
    }

}
