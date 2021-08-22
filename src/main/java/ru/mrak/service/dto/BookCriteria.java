package ru.mrak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;
import lombok.Getter;
import lombok.Setter;
import ru.mrak.web.rest.filter.StringRangeFilter;

/**
 * Criteria class for the {@link ru.mrak.domain.Book} entity. This class is used
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

    // Фильтры
    private LongFilter id;
    private StringFilter title;
    private StringFilter author;
    private StringFilter source;
    private StringFilter text;
    private BooleanFilter publicBook;
    private LongFilter dictionaryId;
    private LongFilter loadedUserId;
    private LongFilter userId;

    private StringFilter titleAuthorFilter; // Строка для поиска по названию и автору
    private BooleanFilter orPublicBookFilter; // Поиск по книгам пользователя или публичным книгам
    private BooleanFilter know95Filter;
    private BooleanFilter know85Filter;
    private BooleanFilter know0Filter;
    private DoubleFilter knowFilter;

    // Сортировки
    private StringRangeFilter startTitle;
    private StringRangeFilter startAuthor;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.publicBook = other.publicBook == null ? null : other.publicBook.copy();
        this.dictionaryId = other.dictionaryId == null ? null : other.dictionaryId.copy();
        this.loadedUserId = other.loadedUserId == null ? null : other.loadedUserId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();

        this.titleAuthorFilter = other.titleAuthorFilter == null ? null : other.titleAuthorFilter.copy();
        this.orPublicBookFilter = other.orPublicBookFilter == null ? null : other.orPublicBookFilter.copy();
        this.know95Filter = other.know95Filter == null ? null : other.know95Filter.copy();
        this.know85Filter = other.know85Filter == null ? null : other.know85Filter.copy();
        this.know0Filter = other.know0Filter == null ? null : other.know0Filter.copy();
        this.knowFilter = other.knowFilter == null ? null : other.knowFilter.copy();

        this.startTitle = other.startTitle == null ? null : other.startTitle.copy();
        this.startAuthor = other.startAuthor == null ? null : other.startAuthor.copy();

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
                Objects.equals(source, that.source) &&
                Objects.equals(text, that.text) &&
                Objects.equals(publicBook, that.publicBook) &&
                Objects.equals(dictionaryId, that.dictionaryId) &&
                Objects.equals(loadedUserId, that.loadedUserId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(titleAuthorFilter, that.titleAuthorFilter) &&
                Objects.equals(orPublicBookFilter, that.orPublicBookFilter) &&
                Objects.equals(know95Filter, that.know95Filter) &&
                Objects.equals(know85Filter, that.know85Filter) &&
                Objects.equals(know0Filter, that.know0Filter) &&
                Objects.equals(knowFilter, that.knowFilter) &&
                Objects.equals(startTitle, that.startTitle) &&
                Objects.equals(startAuthor, that.startAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            author,
            source,
            text,
            publicBook,
            dictionaryId,
            loadedUserId,
            userId,
            titleAuthorFilter,
            orPublicBookFilter,
            know95Filter,
            know85Filter,
            know0Filter,
            knowFilter,
            startTitle,
            startAuthor
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (publicBook != null ? "publicBook=" + publicBook + ", " : "") +
                (dictionaryId != null ? "dictionaryId=" + dictionaryId + ", " : "") +
                (loadedUserId != null ? "loadedUserId=" + loadedUserId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (titleAuthorFilter != null ? "titleAuthor=" + titleAuthorFilter + ", " : "") +
                (orPublicBookFilter != null ? "orPublicBook=" + orPublicBookFilter + ", " : "") +
                (know95Filter != null ? "orPublicBook=" + know95Filter + ", " : "") +
                (know85Filter != null ? "orPublicBook=" + know85Filter + ", " : "") +
                (know0Filter != null ? "orPublicBook=" + know0Filter + ", " : "") +
                (knowFilter != null ? "orPublicBook=" + knowFilter + ", " : "") +
                (startTitle != null ? "orPublicBook=" + startTitle + ", " : "") +
                (startAuthor != null ? "orPublicBook=" + startAuthor + ", " : "") +
            "}";
    }

}
