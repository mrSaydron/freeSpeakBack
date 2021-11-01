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
    private StringRangeFilter word;
    private Filter<PartOfSpeechEnum> partOfSpeech;
    private LongFilter startAmount;
    private Filter<Long> user;

    public WordCriteria() {
    }

    public WordCriteria(WordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.word = other.word == null ? null : other.word.copy();
        this.partOfSpeech = other.partOfSpeech == null ? null : other.partOfSpeech.copy();
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

    public StringRangeFilter getWord() {
        return word;
    }

    public void setWord(StringRangeFilter word) {
        this.word = word;
    }

    public Filter<PartOfSpeechEnum> getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(Filter<PartOfSpeechEnum> partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
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
            Objects.equals(word, that.word) &&
            Objects.equals(partOfSpeech, that.partOfSpeech) &&
            Objects.equals(startAmount, that.startAmount) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, partOfSpeech, startAmount, user);
    }

    @Override
    public String toString() {
        return "WordCriteria{" +
            "id=" + id +
            ", wordFilter=" + word +
            ", partOfSpeechFilter=" + partOfSpeech +
            ", startAmount=" + startAmount +
            ", user=" + user +
            '}';
    }
}
