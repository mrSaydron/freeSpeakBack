package ru.mrak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
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
    private BooleanFilter know100Filter;
    private BooleanFilter know90Filter;
    private BooleanFilter know50Filter;
    private BooleanFilter know0Filter;

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
        this.know100Filter = other.know100Filter == null ? null : other.know100Filter.copy();
        this.know90Filter = other.know90Filter == null ? null : other.know90Filter.copy();
        this.know50Filter = other.know50Filter == null ? null : other.know50Filter.copy();
        this.know0Filter = other.know0Filter == null ? null : other.know0Filter.copy();

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
                Objects.equals(know100Filter, that.know100Filter) &&
                Objects.equals(know90Filter, that.know90Filter) &&
                Objects.equals(know50Filter, that.know50Filter) &&
                Objects.equals(know0Filter, that.know0Filter) &&
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
            know100Filter,
            know90Filter,
            know50Filter,
            know0Filter,
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
                (know100Filter != null ? "orPublicBook=" + know100Filter + ", " : "") +
                (know90Filter != null ? "orPublicBook=" + know90Filter + ", " : "") +
                (know50Filter != null ? "orPublicBook=" + know50Filter + ", " : "") +
                (know0Filter != null ? "orPublicBook=" + know0Filter + ", " : "") +
                (startTitle != null ? "orPublicBook=" + startTitle + ", " : "") +
                (startAuthor != null ? "orPublicBook=" + startAuthor + ", " : "") +
            "}";
    }

}
