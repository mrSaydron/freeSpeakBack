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
 * Criteria class for the {@link ru.mrak.domain.Word} entity. This class is used
 * in {@link ru.mrak.web.rest.WordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /words?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter word;

    private StringFilter translate;

    private StringFilter partOfSpeech;

    public WordCriteria() {
    }

    public WordCriteria(WordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.word = other.word == null ? null : other.word.copy();
        this.translate = other.translate == null ? null : other.translate.copy();
        this.partOfSpeech = other.partOfSpeech == null ? null : other.partOfSpeech.copy();
    }

    @Override
    public WordCriteria copy() {
        return new WordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getWord() {
        return word;
    }

    public void setWord(StringFilter word) {
        this.word = word;
    }

    public StringFilter getTranslate() {
        return translate;
    }

    public void setTranslate(StringFilter translate) {
        this.translate = translate;
    }

    public StringFilter getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(StringFilter partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WordCriteria that = (WordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(word, that.word) &&
            Objects.equals(translate, that.translate) &&
            Objects.equals(partOfSpeech, that.partOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        word,
        translate,
        partOfSpeech
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (word != null ? "word=" + word + ", " : "") +
                (translate != null ? "translate=" + translate + ", " : "") +
                (partOfSpeech != null ? "partOfSpeech=" + partOfSpeech + ", " : "") +
            "}";
    }

}
