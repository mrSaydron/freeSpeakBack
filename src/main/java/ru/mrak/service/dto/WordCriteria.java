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
import ru.mrak.web.rest.filter.StringRangeFilter;

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
    private StringFilter wordFilter;
    private StringFilter partOfSpeechFilter;
    private StringRangeFilter startWord;
    private LongFilter startAmount;

    public WordCriteria() {
    }

    public WordCriteria(WordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.wordFilter = other.wordFilter == null ? null : other.wordFilter.copy();
        this.partOfSpeechFilter = other.partOfSpeechFilter == null ? null : other.partOfSpeechFilter.copy();
        this.startWord = other.startWord == null ? null : other.startWord.copy();
        this.startAmount = other.startAmount == null ? null : other.startAmount.copy();
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

    public StringFilter getWordFilter() {
        return wordFilter;
    }

    public void setWordFilter(StringFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    public StringFilter getPartOfSpeechFilter() {
        return partOfSpeechFilter;
    }

    public void setPartOfSpeechFilter(StringFilter partOfSpeechFilter) {
        this.partOfSpeechFilter = partOfSpeechFilter;
    }

    public StringRangeFilter getStartWord() {
        return startWord;
    }

    public void setStartWord(StringRangeFilter startWord) {
        this.startWord = startWord;
    }

    public LongFilter getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(LongFilter startAmount) {
        this.startAmount = startAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCriteria that = (WordCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(wordFilter, that.wordFilter) &&
            Objects.equals(partOfSpeechFilter, that.partOfSpeechFilter) &&
            Objects.equals(startWord, that.startWord) &&
            Objects.equals(startAmount, that.startAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wordFilter, partOfSpeechFilter, startWord, startAmount);
    }

    @Override
    public String toString() {
        return "WordCriteria{" +
            "id=" + id +
            ", wordFilter=" + wordFilter +
            ", partOfSpeechFilter=" + partOfSpeechFilter +
            ", startWord=" + startWord +
            ", startAmount=" + startAmount +
            '}';
    }
}
