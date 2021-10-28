package ru.mrak.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import ru.mrak.model.entity.Word;
import ru.mrak.model.enumeration.PartOfSpeechEnum;
import ru.mrak.web.rest.filter.StringRangeFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link Word} entity. This class is used
 * in {@link ru.mrak.web.rest.WordContoller} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /words?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    // Фильтры
    private LongFilter id;
    private StringRangeFilter wordFilter;
    private Filter<PartOfSpeechEnum> partOfSpeechFilter;
    private LongFilter startAmount;
    private Filter<Long> user;

    public WordCriteria() {
    }

    public WordCriteria(WordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.wordFilter = other.wordFilter == null ? null : other.wordFilter.copy();
        this.partOfSpeechFilter = other.partOfSpeechFilter == null ? null : other.partOfSpeechFilter.copy();
        this.startAmount = other.startAmount == null ? null : other.startAmount.copy();
        this.user = other.user;
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

    public StringRangeFilter getWordFilter() {
        return wordFilter;
    }

    public void setWordFilter(StringRangeFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    public Filter<PartOfSpeechEnum> getPartOfSpeechFilter() {
        return partOfSpeechFilter;
    }

    public void setPartOfSpeechFilter(Filter<PartOfSpeechEnum> partOfSpeechFilter) {
        this.partOfSpeechFilter = partOfSpeechFilter;
    }

    public LongFilter getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(LongFilter startAmount) {
        this.startAmount = startAmount;
    }

    public Filter<Long> getUser() {
        return user;
    }

    public void setUser(Filter<Long> user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordCriteria that = (WordCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(wordFilter, that.wordFilter) &&
            Objects.equals(partOfSpeechFilter, that.partOfSpeechFilter) &&
            Objects.equals(startAmount, that.startAmount) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wordFilter, partOfSpeechFilter, startAmount, user);
    }

    @Override
    public String toString() {
        return "WordCriteria{" +
            "id=" + id +
            ", wordFilter=" + wordFilter +
            ", partOfSpeechFilter=" + partOfSpeechFilter +
            ", startAmount=" + startAmount +
            ", user=" + user +
            '}';
    }
}
