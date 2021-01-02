package ru.mrak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ru.mrak.domain.Book} entity. This class is used
 * in {@link ru.mrak.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter author;

    private StringFilter source;

    private StringFilter text;

    private BooleanFilter publicBook;

    private LongFilter dictionaryId;

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
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public BooleanFilter getPublicBook() {
        return publicBook;
    }

    public void setPublicBook(BooleanFilter publicBook) {
        this.publicBook = publicBook;
    }

    public LongFilter getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(LongFilter dictionaryId) {
        this.dictionaryId = dictionaryId;
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
            Objects.equals(dictionaryId, that.dictionaryId);
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
        dictionaryId
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
            "}";
    }

}
